package com.eps.todoturtle.devices.logic

import kotlinx.coroutines.channels.Channel

interface DeviceIconActivity {
    fun startIconSelectionLambda(): () -> Unit
    fun getIconChannel(): Channel<Int>
}