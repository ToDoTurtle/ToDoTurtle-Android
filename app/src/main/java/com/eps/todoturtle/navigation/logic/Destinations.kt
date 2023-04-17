package com.eps.todoturtle.navigation.logic

import com.eps.todoturtle.R

const val NOTES_ROUTE = "notes"
const val PROFILE_ROUTE = "profile"
const val DEVICES_ROUTE = "devices"
const val SETTINGS_ROUTE = "settings"
const val INVITE_ROUTE = "invite"

sealed class Destination {
    abstract val route: String
    abstract val labelId: Int
}

object Notes : Destination() {
    override val route = NOTES_ROUTE
    override val labelId = R.string.drawer_notes
}

object Profile : Destination() {
    override val route = PROFILE_ROUTE
    override val labelId = R.string.drawer_profile
}

object Devices : Destination() {
    override val route = DEVICES_ROUTE
    override val labelId = R.string.drawer_devices
}

object Settings : Destination() {
    override val route = SETTINGS_ROUTE
    override val labelId = R.string.drawer_settings
}

object Invite : Destination() {
    override val route = INVITE_ROUTE
    override val labelId = R.string.drawer_invite
}
