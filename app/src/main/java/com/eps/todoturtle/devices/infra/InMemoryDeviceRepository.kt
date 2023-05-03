package com.eps.todoturtle.devices.infra

import com.eps.todoturtle.devices.logic.DeviceRepository
import com.eps.todoturtle.devices.logic.NFCDevice
import okhttp3.internal.toImmutableList

class InMemoryDeviceRepository: DeviceRepository {

    private val devices = mutableListOf<NFCDevice>()

    override suspend fun getAll(): Collection<NFCDevice> = devices.toImmutableList()

    override suspend fun add(device: NFCDevice) {
        devices.add(device)
    }

    override suspend fun remove(device: NFCDevice) {
        devices.remove(device)
    }
}