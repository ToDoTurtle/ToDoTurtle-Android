package com.eps.todoturtle.note.infra

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.eps.todoturtle.note.logic.Note
import com.eps.todoturtle.note.logic.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class NoteStateRepository(private val repository: NoteRepository) {

    private var cached: SnapshotStateList<Note>? = null

    fun getAll(): SnapshotStateList<Note> {
        cacheDataIfNotLoaded()
        return this.cached!!
    }

    fun add(device: Note) {
        assertCacheIsLoadedOperation {
            this@NoteStateRepository.repository.add(device)
            this@NoteStateRepository.cached!!.add(device)
        }
    }

    fun remove(device: Note) {
        assertCacheIsLoadedOperation {
            this@NoteStateRepository.repository.remove(device)
            val deviceToRemove = cached!!.find { it.id == device.id }
            this@NoteStateRepository.cached!!.remove(deviceToRemove)
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