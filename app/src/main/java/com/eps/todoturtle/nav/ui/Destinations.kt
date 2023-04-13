package com.eps.todoturtle.nav.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
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

val screens = listOf(Notes, Profile)
