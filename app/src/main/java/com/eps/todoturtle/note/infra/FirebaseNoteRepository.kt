package com.eps.todoturtle.note.infra

import android.content.Context
import android.os.Looper
import android.widget.Toast
import com.eps.todoturtle.note.logic.DeadlineRepository
import com.eps.todoturtle.note.logic.Note
import com.eps.todoturtle.note.logic.NoteRepository
import com.eps.todoturtle.notifications.AwsNotificationRepository
import com.eps.todoturtle.notifications.NoteNotificationRepository
import com.eps.todoturtle.shared.infra.getDoneNotesCollection
import com.eps.todoturtle.shared.infra.getToDoNotesCollection
import com.eps.todoturtle.shared.logic.forms.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.osmdroid.util.GeoPoint

private const val NOTE_ID = "id"
private const val NOTE_TITLE = "title"
private const val NOTE_DESCRIPTION = "description"
private const val NOTE_NOTIFICATION_TIME = "notificationTime"
private const val NOTE_DEADLINE_TIME = "deadlineTime"
private const val NOTE_LOCATION = "location"
private const val IS_NFC_GENERATED = "nfc"

class FirebaseToDoNoteRepository(
    private val onDeadlineError: () -> Unit = {},
    private val onNotificationError: () -> Unit = {},
    private val notificationRepository: NoteNotificationRepository = AwsNotificationRepository(),
    private val deadlineRepository: DeadlineRepository = AwsDeadlineRepository(),
    private val repo : NoteRepository = FirebaseNoteRepository(getToDoNotesCollection())
) : NoteRepository by repo {

    override suspend fun add(note: Note) {
        val noteAfterDeadline = saveDeadline(note)
        val noteAfterNotificationAndDeadline = saveNotification(noteAfterDeadline)
        repo.add(noteAfterNotificationAndDeadline)
    }

    private suspend fun saveDeadline(note: Note): Note {
        if (note.deadlineTime == null)
            return note
        if (deadlineRepository.saveDeadline(note))
            return note
        onDeadlineError()
        return note.copy(deadlineTime = null)
    }

    private suspend fun saveNotification(note: Note): Note {
        if (note.notificationTime == null)
            return note
        if (notificationRepository.saveNotification(note))
            return note
        onNotificationError()
        return note.copy(notificationTime = null)
    }

    override suspend fun remove(note: Note) {
        if (note.deadlineTime != null)
            deadlineRepository.removeDeadline(note)
        if (note.notificationTime != null)
            notificationRepository.removeNotification(note)
        repo.remove(note)
    }
}

class FirebaseDoneNoteRepository : NoteRepository by FirebaseNoteRepository(getDoneNotesCollection()) {
}

class FirebaseNoteRepository(private val notes: CollectionReference) : NoteRepository {

    override suspend fun getAll(): Collection<Note> {
        return notes.get().await().map { document: QueryDocumentSnapshot ->
            val id = document.getString(NOTE_ID)!!
            val title = document.getString(NOTE_TITLE)!!
            val description = document.getString(NOTE_DESCRIPTION)!!
            val notificationTime = document.getTimestamp(NOTE_NOTIFICATION_TIME)
            val deadlineTime = document.getTimestamp(NOTE_DEADLINE_TIME)
            val location = document.getGeoPoint(NOTE_LOCATION)
            val isNFCGenerated = document.getBoolean(IS_NFC_GENERATED)!!
            Note(
                identifier = id,
                title = title,
                description = description,
                notificationTime = Timestamp.fromGoogleTimestamp(notificationTime),
                deadlineTime = Timestamp.fromGoogleTimestamp(deadlineTime),
                location = location?.let { GeoPoint(it.latitude, it.longitude) },
                isNFCGenerated = isNFCGenerated,
            )
        }.toCollection(mutableListOf())
    }

    override suspend fun add(note: Note) {
        notes.document(note.identifier).set(
            mapOf(
                NOTE_ID to note.identifier,
                NOTE_TITLE to note.title,
                NOTE_DESCRIPTION to note.description,
                NOTE_NOTIFICATION_TIME to note.notificationTime?.toGoogleTimestamp(),
                NOTE_DEADLINE_TIME to note.deadlineTime?.toGoogleTimestamp(),
                NOTE_LOCATION to note.location?.let {
                    com.google.firebase.firestore.GeoPoint(
                        it.latitude,
                        it.longitude,
                    )
                },
                IS_NFC_GENERATED to note.isNFCGenerated,
            ),
        ).await()
    }

    override suspend fun remove(identifier: String) {
        val noteDocument = notes.document(identifier).get().await()
        if (noteDocument.exists()) {
            notes.document(noteDocument.id).delete()
        }
    }
}
