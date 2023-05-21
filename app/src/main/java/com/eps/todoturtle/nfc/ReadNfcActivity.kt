package com.eps.todoturtle.nfc

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.action.infra.FirebaseActionRepository
import com.eps.todoturtle.action.logic.ActionViewModel.Companion.getActionViewModel
import com.eps.todoturtle.action.logic.NoteAction
import com.eps.todoturtle.nfc.ui.AnimatedDisappearingText
import com.eps.todoturtle.note.infra.FirebaseToDoNoteRepository
import com.eps.todoturtle.note.logic.Note
import com.eps.todoturtle.note.logic.location.DefaultLocationClient
import com.eps.todoturtle.note.logic.location.hasLocationPermission
import com.eps.todoturtle.note.logic.location.isGpsEnabled
import com.eps.todoturtle.note.logic.location.toGeoPoint
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.RequestPermissionContext
import com.eps.todoturtle.permissions.logic.providers.CoarseLocationPermissionProvider
import com.eps.todoturtle.permissions.logic.providers.FineLocationPermissionProvider
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ReadNfcActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Firebase.auth.currentUser == null) {
            showMessageAndFinish(resources.getString(R.string.please_login_to_continue))
        }

        val id = getId()
        if (id == null) {
            showMessageAndFinish(resources.getString(R.string.error_reading_nfc_id))
        }

        val actionRepository = FirebaseActionRepository()
        val viewModel = getActionViewModel(actionRepository)
        val action = viewModel.actions[id]
        if (action == null) {
            showMessageAndFinish(resources.getString(R.string.error_device_dont_have_action))
        }

        val notesRepository = FirebaseToDoNoteRepository(onNotificationError = {
            runOnUiThread {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_invalid_notification_time),
                    Toast.LENGTH_LONG,
                ).show()
            }
        }, onDeadlineError = {
            runOnUiThread {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_invalid_deadline_time),
                    Toast.LENGTH_LONG,
                ).show()
            }
        })
        val locationPermissionRequester =
            PermissionRequester(
                this,
                listOf(
                    FineLocationPermissionProvider(this),
                    CoarseLocationPermissionProvider(this),
                ),
            )

        setContent {
            ToDoTurtleTheme(dataStore) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    var isTagRead by rememberSaveable { mutableStateOf(false) }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (!isTagRead) {
                            LoadingAnimation()
                            Spacer(modifier = Modifier.height(30.dp))
                            AnimatedDisappearingText(
                                text = getString(R.string.nfc_tag_read_ngenerating_note),
                            )
                        } else {
                            TagReadAnimation(onFinished = { finish() })
                            Spacer(modifier = Modifier.height(30.dp))
                            Text(text = getString(R.string.done))
                        }
                    }

                    RequestPermissionContext(locationPermissionRequester) {
                        if (action!!.getLocation) {
                            if (!hasLocationPermission()) {
                                requestPermissions()
                            } else if (!isGpsEnabled()) {
                                showMessageAndFinish(resources.getString(R.string.error_gps_disabled))
                            }
                        }
                    }

                    val coroutineScope = rememberCoroutineScope()
                    LaunchedEffect(Unit) {
                        if (action!!.getLocation && hasLocationPermission() && isGpsEnabled()) {
                            coroutineScope.launch {
                                val location = if (action!!.getLocation) {
                                    val locationClient = DefaultLocationClient(
                                        applicationContext,
                                        LocationServices.getFusedLocationProviderClient(
                                            applicationContext,
                                        ),
                                    )
                                    Log.e("ReadNfcActivity", "Getting location")
                                    locationClient.getCurrentLocation().await()
                                } else {
                                    null
                                }
                                Log.e("ReadNfcActivity", "Location received")
                                val note = Note(
                                    identifier = UUID.randomUUID().toString(),
                                    title = action.title,
                                    description = action.description,
                                    deadlineTime = action.deadline,
                                    notificationTime = action.notification,
                                    location = location?.toGeoPoint(),
                                    isNFCGenerated = true,
                                )
                                notesRepository.add(note)
                                Log.e("ReadNfcActivity", "Note added")
                                Log.e("ReadNfcActivity", "Showing feedback")
                                isTagRead = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getId(): String? {
        if (intent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            return getId(intent)
        }
        return null
    }

    @Suppress("DEPRECATION") // Because of the NFC library uses deprecated methods
    private fun getId(intent: Intent): String {
        val parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        with(parcelables) {
            val inNdefMessage = this?.get(0) as NdefMessage
            val inNdefRecord = inNdefMessage.records
            return String(inNdefRecord[0].payload)
        }
    }

    private fun showMessageAndFinish(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

}

@Composable
fun ShowAction(action: NoteAction, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        Text(text = "TODO: CHANGE THIS")
    }
}

@Composable
fun LoadingAnimation(
    circleColor: Color = MaterialTheme.colorScheme.primary,
    animationDelay: Int = 1000
) {

    var circleScale by remember {
        mutableStateOf(0f)
    }

    val circleScaleAnimate = animateFloatAsState(
        targetValue = circleScale,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDelay
            )
        )
    )

    LaunchedEffect(Unit) {
        circleScale = 1f
    }

    Box(
        modifier = Modifier
            .size(size = 64.dp)
            .scale(scale = circleScaleAnimate.value)
            .border(
                width = 4.dp,
                color = circleColor.copy(alpha = 1 - circleScaleAnimate.value),
                shape = CircleShape
            )
    ) {

    }
}

@Composable
fun TagReadAnimation(
    modifier: Modifier = Modifier,
    onFinished: () -> Unit = {},
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

    val ringScale = remember {
        Animatable(0f)
    }
    val ringOpacity = remember {
        Animatable(1f)
    }
    val imageScale = remember {
        Animatable(1f)
    }

    val imageRotation = remember {
        Animatable(0f)
    }

    val orbOffset = remember {
        Animatable(0f)
    }

    val orbScale = remember {
        Animatable(0f)
    }

    val color = remember {
        mutableStateOf(Color.Gray)
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            orbOffset.animateTo(0f)
            ringOpacity.animateTo(1f)
            ringScale.animateTo(0f)
            imageScale.animateTo(0.6f, tween(300, easing = LinearEasing))
            imageRotation.animateTo(-30f, tween(300, easing = LinearEasing))
        }

        coroutineScope.launch {
            delay(300)
            coroutineScope.launch {
                orbOffset.animateTo(orbOffset.value.minus(50f), tween(600))
            }
            color.value = primaryColor
            coroutineScope.launch {
                orbScale.animateTo(1f)
                ringScale.animateTo(1f)
                imageScale.animateTo(1f)
                ringOpacity.animateTo(0f)
                imageRotation.animateTo(0f)
            }
        }

        coroutineScope.launch {
            delay(600)
            orbScale.animateTo(0f, tween(300))
        }

        coroutineScope.launch {
            delay(2000)
            onFinished()
        }
    }

    Surface(
        modifier
            .background(MaterialTheme.colorScheme.onBackground)
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Column(
                Modifier.padding(4.dp)
            ) {

                val offsetEven = with(LocalDensity.current) {
                    orbOffset.value.toDp()
                }
                val offsetOdd = with(LocalDensity.current) {
                    orbOffset.value.toDp()
                        .div(1.2.dp)
                }

                Box {
                    for (item in 0 until 8) {
                        Canvas(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .rotate(item.times(45f))
                                .offset(
                                    x = 0.dp,
                                    y = (if (item % 2 == 0) offsetEven else offsetOdd.dp)
                                )
                                .scale(orbScale.value),
                            onDraw = {
                                drawCircle(
                                    color = secondaryColor,
                                    alpha = if (item % 2 == 0) 1f else 0.5f,
                                    radius = if (item % 2 == 0) 15f else 5f
                                )
                            })
                    }


                    Canvas(modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                        .scale(ringScale.value), onDraw = {
                        drawCircle(
                            color = Color.Gray.copy(alpha = ringOpacity.value),
                            style = Stroke(width = 8f)
                        )
                    })

                    Icon(
                        painter = painterResource(id = R.drawable.add_task),
                        contentDescription = null,
                        tint = color.value, modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp)
                            .scale(imageScale.value)
                            .rotate(imageRotation.value)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoTurtleTheme(LocalContext.current.dataStore) {
        ShowAction(
            NoteAction(
                title = "Dummy Title",
                description = "Dummy Description",
                getLocation = false,
                deadline = null,
                notification = null,
            ),
        )
    }
}
