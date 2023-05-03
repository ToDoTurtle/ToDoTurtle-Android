package com.eps.todoturtle.devices.logic

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

    override suspend fun size(): Int = devices.size

}