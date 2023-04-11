package com.eps.todoturtle.ui.profile.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eps.todoturtle.R
import com.eps.todoturtle.extensions.bitmapFrom
import com.eps.todoturtle.profile.HostageType
import com.eps.todoturtle.profile.ProfileDetails
import com.eps.todoturtle.ui.profile.shared.OutlinedText
import com.eps.todoturtle.ui.profile.shared.ProfileUI

@Composable
fun DetailsUI(
    navController: NavController,
    profileDetails: ProfileDetails = ProfileDetails(
        mutableStateOf("Mock username"),
        mutableStateOf(HostageType.FIRE_HOSTAGE),
        mutableStateOf(bitmapFrom(R.drawable.stickman2_pfp, LocalContext.current)),
    ), // TODO: Remove me, this is just for testing
) {
    ProfileUI {
        DetailsContent(navController, profileDetails)
    }
}

@Composable
fun DetailsContent(
    navController: NavController,
    profileDetails: ProfileDetails,
) {
    val remUsername = rememberSaveable { profileDetails.username }
    val remHostageType = rememberSaveable { profileDetails.hostage }
    val remProfilePicture = remember { profileDetails.profilePicture }

    ProfilePicture(remProfilePicture)
    OutlinedText(remUsername, R.string.username, topPadding = 15)
    HostageTypeProfileField(remHostageType)
    SignOutProfileButton(navController)
}

@Preview(showBackground = true)
@Composable
fun ProfileUIPreview() {
    DetailsUI(rememberNavController())
}
