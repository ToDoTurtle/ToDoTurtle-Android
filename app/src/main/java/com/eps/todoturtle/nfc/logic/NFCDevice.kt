package com.eps.todoturtle.nfc.logic

data class NFCDevice(
    val name: String,
    val description: String,
    val identifier: String,
    val iconResId: Int,
    val configured: Boolean,
)
