package com.eps.todoturtle.navigation.logic

const val DEVICE_WRITE_SUCCESSFUL_PARAMETER = "write_successful"

enum class Destinations(val route: String) {
    LOGIN("login"),
    PROFILE("profile"),
    NOTES("notes"),
    SETTINGS("settings"),
    WRITE_DEVICE("write_device"),
    INVITE("invite"),
    DEVICES("devices/{$DEVICE_WRITE_SUCCESSFUL_PARAMETER}"),
    DEVICES_WRITE_SUCCESSFUL("devices/true"),
    DEVICES_NORMAL("devices/false"),
}
