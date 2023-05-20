package com.eps.todoturtle.note.infra

import com.eps.todoturtle.note.logic.Note
import com.eps.todoturtle.note.logic.NoteRepository
import okhttp3.internal.toImmutableList

class InMemoryNoteRepository : NoteRepository {
    private val notes = mutableListOf<Note>(
        Note(
            identifier = "1",
            title = "Note title",
            description = "Note description",
        ),
    )

    override suspend fun getAll(): Collection<Note> = notes.toImmutableList()

    override suspend fun add(note: Note) {
        notes.add(note)
    }

    override suspend fun remove(note: Note) {
        val currentDevice = notes.find { it.identifier == note.identifier }
        notes.remove(currentDevice)
    }
}
