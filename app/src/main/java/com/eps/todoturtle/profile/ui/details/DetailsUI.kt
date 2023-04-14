package com.eps.todoturtle.profile.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.logic.ProfileDetails
import com.eps.todoturtle.profile.logic.ProfileViewModel
import com.eps.todoturtle.profile.ui.shared.ProfileUI

@Composable
fun DetailsUI(
    hasPermissions: () -> Boolean,
    requestPermissions: () -> Unit,
    onSignOutClick: () -> Unit,
    profileViewModel: ProfileViewModel,
) {
    ProfileUI {
        DetailsContent(
            hasPermissions = hasPermissions,
            requestPermissions = requestPermissions,
            onSignOutClick = onSignOutClick,
            profileViewModel = profileViewModel,
        )
    }
}

@Composable
fun DetailsContent(
    hasPermissions: () -> Boolean,
    requestPermissions: () -> Unit,
    onSignOutClick: () -> Unit,
    profileViewModel: ProfileViewModel,
) {
    val details = profileViewModel.details.collectAsState().value

    ProfilePicture(
        hasPermissions = hasPermissions,
        requestPermissions = requestPermissions,
        profilePicture = details.profilePicture
    ) {
        profileViewModel.changeProfilePicture(it)
    }
    ProfileOutlinedText(details.username, R.string.username, topPadding = 15) {
        profileViewModel.changeUsername(it)
    }
    ProfileOutlinedText(details.mail, R.string.mail, topPadding = 15) {
        profileViewModel.changeMail(it)
    }
    HostageTypeProfileField(details.hostage) {
        profileViewModel.changeHostageType(it)
    }
    SignOutProfileButton(onSignOutClick)
}

@Preview(showBackground = true)
@Composable
fun ProfileUIPreview() {
    DetailsUI(
        hasPermissions = { true },
        requestPermissions = {},
        onSignOutClick = {},
        profileViewModel = ProfileViewModel(LocalContext.current),
    )
}
