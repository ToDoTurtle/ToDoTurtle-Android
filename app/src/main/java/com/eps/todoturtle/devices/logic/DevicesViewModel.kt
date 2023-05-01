package com.eps.todoturtle.devices.logic

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.eps.todoturtle.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking

class DevicesViewModel private constructor() : ViewModel() {

    private lateinit var iconSelection: () -> Unit
    private lateinit var iconChannel: Channel<Int>

    private val scope = SupervisorJob() + Dispatchers.IO
    val deviceBuilder = DeviceBuilder()

    val deviceErrors: MutableStateFlow<Collection<DeviceBuildError>> = MutableStateFlow(emptyList())
    private val deviceCreator: Channel<NFCDevice> = Channel()
    val deviceCreated: Flow<NFCDevice> = deviceCreator.receiveAsFlow()

    fun getDevices(): List<NFCDevice> {
        return listOf(
            NFCDevice(
                name = "Car",
                description = "My car",
                identifier = "1234567890",
                iconResId = R.drawable.car,
                true,
            ),
            NFCDevice(
                name = "Kitchen",
                description = "My Kitchen",
                identifier = "1234567890",
                iconResId = R.drawable.headphones,
                false,
            ),
        )
    }

    private fun setIconSelection(iconSelection: () -> Unit) {
        this.iconSelection = iconSelection
    }

    private fun setIconChannel(iconChannel: Channel<Int>) {
        if (scope.isActive) scope.cancel()
        this.iconChannel = iconChannel
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun buildDevice() {
        if (!iconChannel.isEmpty)
            runBlocking { deviceBuilder.iconResId = iconChannel.receive() }
        else
            deviceBuilder.iconResId = null
        when (val result = deviceBuilder.build()) {
            is DeviceBuildResult.Success -> {
                runBlocking(Dispatchers.IO) { deviceCreator.send(result.device) }
            }
            is DeviceBuildResult.Failure -> { deviceErrors.value = result.errors }
        }
    }

    fun showIconSelection() = iconSelection()

    companion object {
        fun <T> T.getDevicesViewModel(): DevicesViewModel where T : DeviceIconActivity, T : ComponentActivity {
            val viewModel = DevicesViewModel()
            viewModel.setIconSelection(this.startIconSelectionLambda())
            viewModel.setIconChannel(this.getIconChannel())
            return viewModel
        }
    }

}
