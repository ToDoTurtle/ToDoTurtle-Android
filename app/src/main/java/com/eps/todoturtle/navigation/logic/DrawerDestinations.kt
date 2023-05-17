package com.eps.todoturtle.navigation.logic

import com.eps.todoturtle.R

sealed class Destination {
    abstract val route: String
    abstract val labelId: Int
}

object Notes : Destination() {
    override val route = Destinations.NOTES.route
    override val labelId = R.string.drawer_notes
}

object Profile : Destination() {
    override val route = Destinations.PROFILE.route
    override val labelId = R.string.drawer_profile
}

object Devices : Destination() {
    override val route = Destinations.DEVICES_NORMAL.route
    override val labelId = R.string.drawer_devices
}

object Settings : Destination() {
    override val route = Destinations.SETTINGS.route
    override val labelId = R.string.drawer_settings
}

object Invite : Destination() {
    override val route = Destinations.ABOUT_US.route
    override val labelId = R.string.drawer_about_us
}
