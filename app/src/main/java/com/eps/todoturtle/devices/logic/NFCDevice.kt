package com.eps.todoturtle.devices.logic

data class NFCDevice(
    val name: String,
    val description: String,
    val identifier: String,
    val iconResId: Int,
    val configured: Boolean,
)
