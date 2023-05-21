package com.eps.todoturtle.nfc

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.eps.todoturtle.R
import com.eps.todoturtle.action.infra.FirebaseActionRepository
import com.eps.todoturtle.action.logic.ActionViewModel.Companion.getActionViewModel
import com.eps.todoturtle.action.logic.NoteAction
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
                    if (!isTagRead) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(text = "Reading NFC tag")
                        }
                    } else ShowAction(action!!)

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
