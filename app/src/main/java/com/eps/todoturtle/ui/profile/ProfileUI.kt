@file:OptIn(ExperimentalMaterial3Api::class)

package com.eps.todoturtle.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.HostageType
import com.eps.todoturtle.profile.ProfileDetails
import com.eps.todoturtle.ui.profile.picture.ProfilePicture
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme

@Composable
fun ProfileUI(
    profileDetails: ProfileDetails = ProfileDetails(
        mutableStateOf("Mock username"),
        mutableStateOf(HostageType.FIRE_HOSTAGE),
        mutableStateOf(ProfileDetails.bitmapFrom(R.drawable.stickman2_pfp, LocalContext.current)),
    ), // FIXME: Mock data
) {
    val remUsername = rememberSaveable { profileDetails.username }
    val remHostageType = rememberSaveable { profileDetails.hostage }
    val remProfilePicture = remember { profileDetails.profilePicture }
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
                        ProfilePicture(remProfilePicture)
                        UsernameProfileField(remUsername)
                        HostageTypeProfileField(remHostageType)
                        SignOutProfileButton()
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
