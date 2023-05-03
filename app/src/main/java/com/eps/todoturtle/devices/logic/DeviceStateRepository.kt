package com.eps.todoturtle.devices.logic

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class DeviceStateRepository(private val repository: DeviceRepository) {

    private var cached: SnapshotStateList<NFCDevice>? = null

    fun getAll(): SnapshotStateList<NFCDevice> {
        cacheDataIfNotLoaded()
        return this.cached!!
    }

    fun add(device: NFCDevice) {
        cacheDataIfNotLoaded()
        runBlocking(Dispatchers.IO){
            this@DeviceStateRepository.repository.add(device)
            this@DeviceStateRepository.cached!!.add(device)
        }
    }

    fun remove(device: NFCDevice) {
        cacheDataIfNotLoaded()
        runBlocking(Dispatchers.IO){
            this@DeviceStateRepository.repository.remove(device)
            this@DeviceStateRepository.cached!!.remove(device)
        }
    }

    private fun cacheDataIfNotLoaded() {
        this.cached = cached ?: getAllFromRepository().toMutableStateList()
    }

    private fun getAllFromRepository(): Collection<NFCDevice> {
        return runBlocking(Dispatchers.IO) { repository.getAll() }
    }
}