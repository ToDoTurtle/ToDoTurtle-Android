package com.eps.todoturtle.nfc.logic

import androidx.lifecycle.ViewModel
import com.eps.todoturtle.R

class DevicesViewModel: ViewModel() {

    fun getDevices(): List<NFCDevice> {
        return listOf(
            NFCDevice(name = "Car", description = "My car", identifier = "1234567890", iconResId = R.drawable.car, true),
            NFCDevice(name = "Kitchen", description = "My Kitchen", identifier = "1234567890", iconResId = R.drawable.headphones, false),
        )
    }

    fun addDevice(
        name: String,
        description: String,
        identifier: String,
        iconResId: Int
    ) {
    }

}