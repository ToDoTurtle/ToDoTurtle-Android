package com.eps.todoturtle.nav.ui

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
    override val route = "notes"
    override val label = "Notes"
}

object Profile : Destination {
    override val icon = Icons.Filled.Person
    override val route = "profile"
    override val label = "Profile"
}

object Devices : Destination {
    override val icon = Icons.Filled.Star
    override val route = "devices"
    override val label = "Devices"
}

object Settings : Destination {
    override val icon = Icons.Filled.Settings
    override val route = "settings"
    override val label = "Settings"
}

object Invite : Destination {
    override val icon = Icons.Filled.AddCircle
    override val route = "invite"
    override val label = "Invite a friend"
}

val screens = listOf(Notes, Devices, Profile, Settings, Invite)
