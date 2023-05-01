package com.eps.todoturtle.devices.logic

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.eps.todoturtle.R
import kotlinx.coroutines.flow.Flow

class DevicesViewModel private constructor() : ViewModel() {

    private lateinit var iconSelection: () -> Unit
    private lateinit var iconFlow: Flow<Int?>

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

    private fun setIconFlow(iconFlow: Flow<Int?>) {
        this.iconFlow = iconFlow
    }

    fun showIconSelection() = iconSelection()

    companion object {
        fun <T> T.getDevicesViewModel(): DevicesViewModel where T : DeviceIconActivity, T : ComponentActivity {
            val viewModel = DevicesViewModel()
            viewModel.setIconSelection(this.startIconSelectionLambda())
            viewModel.setIconFlow(this.getIconFlow())
            return viewModel
        }
    }

}
