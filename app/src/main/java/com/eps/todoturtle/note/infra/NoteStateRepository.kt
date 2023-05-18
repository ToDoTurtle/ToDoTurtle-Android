package com.eps.todoturtle.note.infra

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.eps.todoturtle.note.logic.NoteIMpl
import com.eps.todoturtle.note.logic.NoteRepository

class NoteStateRepository(private val noteRepository: NoteRepository) {
    private var cached: SnapshotStateList<NoteIMpl>? = null

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