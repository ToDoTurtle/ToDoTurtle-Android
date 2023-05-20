package com.eps.todoturtle.notifications

import com.eps.todoturtle.note.logic.Note

interface NoteNotificationRepository {

    /* Returns true if notification is saved successfully, false if it can't be saved*/
    suspend fun saveNotification(note: Note): Boolean

    suspend fun removeNotification(note: Note)

}