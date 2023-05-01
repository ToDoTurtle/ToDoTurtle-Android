package com.eps.todoturtle.devices.logic

import kotlinx.coroutines.flow.Flow

interface DeviceIconActivity {
    fun startIconSelectionLambda(): () -> Unit
    fun getIconFlow(): Flow<Int?>
}