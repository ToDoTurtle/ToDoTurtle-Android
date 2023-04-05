@file:OptIn(ExperimentalMaterial3Api::class)

package com.eps.todoturtle.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eps.todoturtle.R
import com.eps.todoturtle.pfp.HostageType
import com.eps.todoturtle.pfp.ProfileDetails
import com.eps.todoturtle.ui.theme.PurpleGrey40
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme

@Composable
fun ProfileUI(
    profileDetails: ProfileDetails = ProfileDetails(
        mutableStateOf("Mock username"),
        mutableStateOf(HostageType.FIRE_HOSTAGE),
        mutableStateOf(R.drawable.stickman2_pfp)
    ),
) {
    var remUsername by rememberSaveable { profileDetails.username }
    var remHostageType by rememberSaveable { profileDetails.hostage }
    val remProfilePicture by rememberSaveable { profileDetails.profilePicture }
    val focusManager = LocalFocusManager.current
    var expandedDropdown by rememberSaveable { mutableStateOf(false) }
    var shouldShowDialog by rememberSaveable { mutableStateOf(false) }
    ToDoTurtleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Card(
                    modifier = Modifier
                        .height(500.dp)
                        .width(320.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(30.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(200.dp),
                        ) {
                            // This is to control box's width/height
                            Card(
                                modifier = Modifier.fillMaxWidth(),
//                                shape = RoundedCornerShape(18.dp),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 8.dp,
                                ),
                            ) {
                                Image(
                                    painter = painterResource(id = remProfilePicture),
                                    contentDescription = stringResource(id = R.string.profile_picture_desc),
                                    modifier = Modifier.clickable { shouldShowDialog = true },
                                )
                                if (shouldShowDialog) {
                                    Dialog(onDismissRequest = { shouldShowDialog = false }) {
                                        Card(modifier = Modifier
                                            .width(300.dp)
                                            .height(300.dp)) {
                                            Column(
                                                modifier = Modifier.fillMaxSize(),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                            ) {
                                                Spacer(modifier = Modifier.height(20.dp))
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.5f)
                                                        .height(150.dp)
                                                ) {
                                                    Card(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        shape = RoundedCornerShape(18.dp),
                                                        elevation = CardDefaults.cardElevation(
                                                            defaultElevation = 8.dp,
                                                        )
                                                    ) {
                                                        Image(
                                                            painter = painterResource(id = remProfilePicture),
                                                            contentDescription = stringResource(id = R.string.profile_picture_desc),
                                                        )
                                                    }
                                                }
                                                Spacer(modifier = Modifier.height(20.dp))
                                                Text(
                                                    text = "Choosen image",
                                                    fontWeight = FontWeight.ExtraBold,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier.fillMaxWidth(),
                                                    style = MaterialTheme.typography.headlineMedium,
                                                    maxLines = 2,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Spacer(modifier = Modifier.height(20.dp))
                                                Row(
                                                    Modifier
                                                        .fillMaxWidth()
                                                        .padding(top = 10.dp)
                                                        .background(color = MaterialTheme.colorScheme.onPrimary),
                                                    horizontalArrangement = Arrangement.SpaceAround
                                                ) {
                                                    val context = LocalContext.current
                                                    TextButton(onClick = {
                                                        Toast.makeText(
                                                            context,
                                                            "Camera",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }) {

                                                        Text(
                                                            "Camera",
                                                            fontWeight = FontWeight.Bold,
                                                            modifier = Modifier.padding(
                                                                top = 5.dp,
                                                                bottom = 5.dp
                                                            )
                                                        )
                                                    }
                                                    TextButton(onClick = {
                                                        Toast.makeText(
                                                            context,
                                                            "Gallery",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }) {
                                                        Text(
                                                            "Gallery",
                                                            fontWeight = FontWeight.Bold,
                                                            modifier = Modifier.padding(
                                                                top = 5.dp,
                                                                bottom = 5.dp
                                                            )
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        OutlinedTextField(
                            value = remUsername,
                            onValueChange = { remUsername = it },
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            label = { Text(stringResource(id = R.string.username)) },
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        ExposedDropdownMenuBox(expanded = expandedDropdown,
                            onExpandedChange = { expandedDropdown = !expandedDropdown }) {
                            OutlinedTextField(
                                modifier = Modifier.menuAnchor(),
                                readOnly = true,
                                value = remHostageType.getString(LocalContext.current),
                                onValueChange = {},
                                label = { Text(stringResource(id = R.string.hostage)) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown)
                                },
                            )
                            ExposedDropdownMenu(expanded = expandedDropdown,
                                modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
                                onDismissRequest = { expandedDropdown = false }) {
                                HostageType.values().forEach { hostageType ->
                                    DropdownMenuItem(text = {
                                        Text(
                                            text = hostageType.getString(
                                                LocalContext.current
                                            )
                                        )
                                    }, onClick = {
                                        remHostageType = hostageType
                                        expandedDropdown = false
                                    })
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Button(onClick = { /*TODO*/ }) { // This will take the user to the login screen
                            Text(text = stringResource(id = R.string.sign_out))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileUIPreview() {
    ProfileUI()
}
