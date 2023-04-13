package com.eps.todoturtle.profile.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.logic.HostageType
import com.eps.todoturtle.profile.logic.ProfileDetails
import com.eps.todoturtle.profile.ui.shared.OutlinedText
import com.eps.todoturtle.profile.ui.shared.ProfileUI
import com.eps.todoturtle.shared.logic.extensions.bitmapFrom

@Composable
fun DetailsUI(
    hasPermissions: () -> Boolean,
    requestPermissions: () -> Unit,
    onSignOutClick: () -> Unit,
    profileDetails: ProfileDetails = ProfileDetails(
        mutableStateOf("Mock username"),
        mutableStateOf(HostageType.FIRE_HOSTAGE),
        mutableStateOf(bitmapFrom(R.drawable.stickman2_pfp, LocalContext.current)),
    ), // TODO: Remove me, this is just for testing
) {
    ProfileUI {
        DetailsContent(
            hasPermissions = hasPermissions,
            requestPermissions = requestPermissions,
            onSignOutClick = onSignOutClick,
            profileDetails = profileDetails,
        )
    }
}

@Composable
fun DetailsContent(
    hasPermissions: () -> Boolean,
    requestPermissions: () -> Unit,
    onSignOutClick: () -> Unit,
    profileDetails: ProfileDetails,
) {
    val remUsername = rememberSaveable { profileDetails.username }
    val remHostageType = rememberSaveable { profileDetails.hostage }
    val remProfilePicture = remember { profileDetails.profilePicture }

    ProfilePicture(
        hasPermissions = hasPermissions,
        requestPermissions = requestPermissions,
        profilePicture = remProfilePicture,
    )
    OutlinedText(remUsername, R.string.username, topPadding = 15)
    HostageTypeProfileField(remHostageType)
    SignOutProfileButton(onSignOutClick)
}

@Preview(showBackground = true)
@Composable
fun ProfileUIPreview() {
    DetailsUI(
        hasPermissions = { true },
        requestPermissions = {},
        onSignOutClick = {},
    )
}
