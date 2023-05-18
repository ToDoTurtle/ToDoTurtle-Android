package com.eps.todoturtle.note.logic

interface NoteRepository {
    suspend fun create(note: Note)
    suspend fun getAll(): Collection<Note>
    suspend fun remove(note: Note)
}