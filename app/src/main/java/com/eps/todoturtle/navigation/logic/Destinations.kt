package com.eps.todoturtle.navigation.logic

enum class Destinations(val route: String) {
    LOGIN("login"),
    PROFILE("profile"),
    NOTES("notes"),
    SETTINGS("settings"),
    WRITE_DEVICE("write_device"),
    INVITE("invite"),
    DEVICES_WRITE_SUCCESSFUL_PARAM("write_successful"),
    DEVICES("devices/{$DEVICES_WRITE_SUCCESSFUL_PARAM}"),
    DEVICES_WRITE_SUCCESSFUL("devices/true"),
    DEVICES_NORMAL("devices/false"),
    DEVICE_CONFIGURATION("device_configuration"),
}