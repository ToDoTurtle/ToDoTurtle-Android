package com.eps.todoturtle.nav.logic

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

interface Destination {
    val icon: ImageVector
    val route: String
    val label: String
}

object Notes : Destination {
    override val icon = Icons.Filled.Edit
    override val route = DestinationConstants.NOTES_ROUTE
    override val label = DestinationConstants.NOTES_LABEL
}

object Profile : Destination {
    override val icon = Icons.Filled.Person
    override val route = DestinationConstants.PROFILE_ROUTE
    override val label = DestinationConstants.PROFILE_LABEL
}

object Devices : Destination {
    override val icon = Icons.Filled.Star
    override val route = DestinationConstants.DEVICES_ROUTE
    override val label = DestinationConstants.DEVICES_LABEL
}

object Settings : Destination {
    override val icon = Icons.Filled.Settings
    override val route = DestinationConstants.SETTINGS_ROUTE
    override val label = DestinationConstants.SETTINGS_LABEL
}

object Invite : Destination {
    override val icon = Icons.Filled.AddCircle
    override val route = DestinationConstants.INVITE_ROUTE
    override val label = DestinationConstants.INVITE_LABEL
}

val screens = listOf(Notes, Devices, Profile, Settings, Invite)
