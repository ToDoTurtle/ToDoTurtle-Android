package com.eps.todoturtle.profile.ui.details

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.logic.ProfileViewModel
import com.eps.todoturtle.profile.ui.shared.ProfileUI

@Composable
fun DetailsUI(
    hasPermissions: () -> Boolean,
    requestPermissions: () -> Unit,
    profileViewModel: ProfileViewModel,
    onSignOutClick: () -> Unit,
) {
    ProfileUI {
        DetailsContent(
            hasPermissions = hasPermissions,
            requestPermissions = requestPermissions,
            profileViewModel = profileViewModel,
        ) {
            onSignOutClick()
        }
    }
}

@Composable
fun DetailsContent(
    hasPermissions: () -> Boolean,
    requestPermissions: () -> Unit,
    profileViewModel: ProfileViewModel,
    onSignOutClick: () -> Unit,
) {
    val details by profileViewModel.details.collectAsState()

    ProfilePicture(
        hasPermissions = hasPermissions,
        requestPermissions = requestPermissions,
        profilePicture = details.profilePicture,
    ) {
        profileViewModel.changeProfilePicture(it)
    }
    ProfileOutlinedText(details.username, R.string.profile_username, topPadding = 15) {
        profileViewModel.changeUsername(it)
    }
    ProfileOutlinedText(details.mail, R.string.profile_mail, topPadding = 15) {
        profileViewModel.changeMail(it)
    }
    HostageTypeProfileField(details.hostage) {
        profileViewModel.changeHostageType(it)
    }
    SignOutProfileButton {
        onSignOutClick()
    }
    Spacer(modifier = Modifier.size(5.dp))
}
