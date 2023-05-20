package com.eps.todoturtle.note.logic

interface DeadlineRepository {

    /* Returns true if deadline is saved successfully, false if it can't be saved */
    suspend fun saveDeadline(note: Note): Boolean
    suspend fun removeDeadline(note: Note)
}