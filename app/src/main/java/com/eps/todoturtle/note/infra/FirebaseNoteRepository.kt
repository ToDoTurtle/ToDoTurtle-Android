package com.eps.todoturtle.note.infra

import com.eps.todoturtle.note.logic.Note
import com.eps.todoturtle.note.logic.NoteRepository
import com.eps.todoturtle.shared.infra.getDoneNotesCollection
import com.eps.todoturtle.shared.infra.getToDoNotesCollection
import com.eps.todoturtle.shared.logic.forms.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.tasks.await
import org.osmdroid.util.GeoPoint

private const val NOTE_ID = "id"
private const val NOTE_TITLE = "title"
private const val NOTE_DESCRIPTION = "description"
private const val NOTE_NOTIFICATION_TIME = "notificationTime"
private const val NOTE_DEADLINE_TIME = "deadlineTime"
private const val NOTE_LOCATION = "location"

class FirebaseToDoNoteRepository : FirebaseNoteRepository() {
    override fun getNotesCollection(): CollectionReference {
        return getToDoNotesCollection()
    }
}

class FirebaseDoneNoteRepository : FirebaseNoteRepository() {
    override fun getNotesCollection(): CollectionReference {
        return getDoneNotesCollection()
    }
}

abstract class FirebaseNoteRepository : NoteRepository {

    abstract fun getNotesCollection(): CollectionReference

    override suspend fun getAll(): Collection<Note> {
        val notes = getNotesCollection()
        return notes.get().await().map { document: QueryDocumentSnapshot ->
            val id = document.getString(NOTE_ID)!!
            val title = document.getString(NOTE_TITLE)!!
            val description = document.getString(NOTE_DESCRIPTION)!!
            val notificationTime = document.getTimestamp(NOTE_NOTIFICATION_TIME)
            val deadlineTime = document.getTimestamp(NOTE_DEADLINE_TIME)
            val location = document.getGeoPoint(NOTE_LOCATION)
            Note(
                identifier = id,
                title = title,
                description = description,
                notificationTime = Timestamp.fromGoogleTimestamp(notificationTime),
                deadlineTime = Timestamp.fromGoogleTimestamp(deadlineTime),
                location = location?.let { GeoPoint(it.latitude, it.longitude) }
            )
        }.toCollection(mutableListOf())
    }

    override suspend fun add(note: Note) {
        val notes = getNotesCollection()
        notes.add(
            mapOf(
                NOTE_ID to note.identifier,
                NOTE_TITLE to note.title,
                NOTE_DESCRIPTION to note.description,
                NOTE_NOTIFICATION_TIME to note.notificationTime?.toGoogleTimestamp(),
                NOTE_DEADLINE_TIME to note.deadlineTime?.toGoogleTimestamp(),
                NOTE_LOCATION to note.location?.let {
                    com.google.firebase.firestore.GeoPoint(
                        it.latitude,
                        it.longitude
                    )
                },
            ),
        ).await()
    }

    override suspend fun remove(note: Note) {
        val notes = getNotesCollection()
        val documentToDelete = notes.whereEqualTo(NOTE_ID, note.identifier).get().await()
        documentToDelete.forEach { document ->
            notes.document(document.id).delete()
        }
    }
}
