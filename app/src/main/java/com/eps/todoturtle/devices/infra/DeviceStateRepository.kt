package com.eps.todoturtle.devices.infra

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.eps.todoturtle.devices.logic.DeviceRepository
import com.eps.todoturtle.devices.logic.NFCDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/*
* This class creates a cache between the "real" repository and the compose components.
* This allows the compose app to react to changes without reloading all the devices from the
* original repository (once again).
 */
class DeviceStateRepository(private val repository: DeviceRepository) {

    private var cached: SnapshotStateList<NFCDevice>? = null

    fun getAll(): SnapshotStateList<NFCDevice> {
        cacheDataIfNotLoaded()
        return this.cached!!
    }

    fun add(device: NFCDevice) {
        assertCacheIsLoadedOperation {
            this@DeviceStateRepository.repository.add(device)
            this@DeviceStateRepository.cached!!.add(device)
        }
    }

    fun remove(device: NFCDevice) {
        assertCacheIsLoadedOperation {
            this@DeviceStateRepository.repository.remove(device)
            val deviceToRemove = cached!!.find { it.identifier == device.identifier }
            this@DeviceStateRepository.cached!!.remove(deviceToRemove)
        }
    }

    private fun cacheDataIfNotLoaded() {
        this.cached = cached ?: getAllFromRepository().toMutableStateList()
    }

    private fun getAllFromRepository() = runBlocking(Dispatchers.IO) { repository.getAll() }

    private fun assertCacheIsLoadedOperation(
        operation: suspend () -> Unit,
    ) {
        cacheDataIfNotLoaded()
        runBlocking(Dispatchers.IO) {
            operation()
        }
    }
}
