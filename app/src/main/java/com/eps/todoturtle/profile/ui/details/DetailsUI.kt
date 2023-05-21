package com.eps.todoturtle.profile.ui.details

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.logic.ProfileViewModel
import com.eps.todoturtle.profile.logic.UserAuth
import com.eps.todoturtle.profile.ui.shared.ProfileUI

@Composable
fun DetailsUI(
    hasPermissions: () -> Boolean,
    requestPermissions: () -> Unit,
    userAuth: UserAuth,
    profileViewModel: ProfileViewModel,
    onSignOutClick: () -> Unit,
) {
    ProfileUI {
        DetailsContent(
            hasPermissions = hasPermissions,
            userAuth = userAuth,
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
    userAuth: UserAuth,
    onSignOutClick: () -> Unit,
) {
    val details by profileViewModel.details.collectAsState()
    val context = LocalContext.current

    ProfilePicture(
        hasPermissions = hasPermissions,
        requestPermissions = requestPermissions,
        profilePicture = details.profilePicture,
    ) {
        profileViewModel.changeProfilePicture(it)
    }
    ProfileOutlinedText(userAuth.getMail(), R.string.profile_mail, topPadding = 15) {
        // profileViewModel.changeMail(it)
        Toast.makeText(
            context,
            context.getString(R.string.mail_not_changable_still),
            Toast.LENGTH_SHORT,
        ).show()
    }
    HostageTypeProfileField(details.hostage) {
        profileViewModel.changeHostageType(it)
    }
    SignOutProfileButton {
        onSignOutClick()
    }
    Spacer(modifier = Modifier.size(5.dp))
}
