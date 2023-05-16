package com.eps.todoturtle.devices.logic

interface DeviceRepository {
    suspend fun getAll(): Collection<NFCDevice>
    suspend fun add(device: NFCDevice)
    suspend fun remove(device: NFCDevice)
}
