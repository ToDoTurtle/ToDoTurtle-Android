package com.eps.todoturtle.devices.infra

import com.eps.todoturtle.R
import com.eps.todoturtle.devices.logic.DeviceRepository
import com.eps.todoturtle.devices.logic.NFCDevice
import okhttp3.internal.toImmutableList

class InMemoryDeviceRepository : DeviceRepository {

    private val devices = mutableListOf<NFCDevice>(
        NFCDevice(
            name = "Test",
            description = "Dummy Description",
            identifier = "fakeidentifier",
            iconResId = R.drawable.car,
        ),
    )

    override suspend fun getAll(): Collection<NFCDevice> = devices.toImmutableList()

    override suspend fun add(device: NFCDevice) {
        devices.add(device)
    }

    override suspend fun remove(device: NFCDevice) {
        val currentDevice = devices.find { it.identifier == device.identifier }
        devices.remove(currentDevice)
    }
}
