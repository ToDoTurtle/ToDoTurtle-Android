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

    fun add(note: Note) {
        assertCacheIsLoadedOperation {
            this@NoteStateRepository.repository.add(note)
            this@NoteStateRepository.cached!!.add(note)
        }
    }

    fun remove(note: Note) {
        assertCacheIsLoadedOperation {
            this@NoteStateRepository.repository.remove(note)
            val noteToRemove = cached!!.find { it.identifier == note.identifier }
            this@NoteStateRepository.cached!!.remove(noteToRemove)
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
