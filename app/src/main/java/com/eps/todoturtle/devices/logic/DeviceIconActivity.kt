package com.eps.todoturtle.devices.logic

import android.graphics.drawable.Drawable
import kotlinx.coroutines.channels.Channel

interface DeviceIconActivity {
    fun startIconSelectionLambda(): () -> Unit
    fun getIconChannel(): Channel<Int>
    fun getIconDrawable(): (id: Int) -> Drawable?
}