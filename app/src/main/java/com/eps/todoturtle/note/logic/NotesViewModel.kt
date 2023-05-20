package com.eps.todoturtle.note.logic

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eps.todoturtle.note.infra.FirebaseDoneNoteRepository
import com.eps.todoturtle.note.infra.FirebaseToDoNoteRepository
import com.eps.todoturtle.note.infra.NoteStateRepository
import com.eps.todoturtle.shared.logic.forms.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import org.osmdroid.util.GeoPoint

class NotesViewModel(
    toDoNotesRepository: NoteRepository,
    doneNotesRepository: NoteRepository,
) : ViewModel() {
    private val noteBuilder = NoteBuilder()
    val noteErrors: MutableStateFlow<List<NoteBuildError>> = MutableStateFlow(emptyList())
    var selectedNote: Note? = null
    var noteTitle = mutableStateOf("")
    var noteDescription = mutableStateOf("")
    var noteNotificationTime: Timestamp? = null
    var noteDeadlineTime: Timestamp? = null
    var noteLocation: GeoPoint? = null

    private val toDoRepository = NoteStateRepository(toDoNotesRepository)
    private val doneRepository = NoteStateRepository(doneNotesRepository)

    companion object {
        @Suppress("UNCHECKED_CAST")
        private class NoteScreenViewModelFactory(
            val toDoNotesRepository: NoteRepository,
            val doneNotesRepository: NoteRepository,
        ) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NotesViewModel(
                    toDoNotesRepository = toDoNotesRepository,
                    doneNotesRepository = doneNotesRepository
                ) as T
            }
        }

        fun <T> T.getNoteScreenViewModel(
            onDeadlineError: () -> Unit,
            onNotificationError: () -> Unit,
        ): NotesViewModel where T : ComponentActivity {
            return ViewModelProvider(
                this,
                NoteScreenViewModelFactory(
                    toDoNotesRepository = FirebaseToDoNoteRepository(onDeadlineError=onDeadlineError, onNotificationError=onNotificationError),
                    doneNotesRepository = FirebaseDoneNoteRepository()
                ),
            )[NotesViewModel::class.java]
        }
    }

    fun load(note: Note) {
        selectedNote = note
        noteTitle.value = note.title
        noteDescription.value = note.description
        noteNotificationTime = note.notificationTime
        noteDeadlineTime = note.deadlineTime
        noteLocation = note.location
    }

    fun deleteDone(note: Note) = doneRepository.remove(note)

    fun deleteToDo(note: Note) = toDoRepository.remove(note)

    fun updateDone() = updateNote(doneRepository)

    fun updateToDo() = updateNote(toDoRepository)

    private fun updateNote(repository: NoteStateRepository) {
        noteBuilder.title.value = noteTitle.value
        noteBuilder.description.value = noteDescription.value
        noteBuilder.notificationTime = noteNotificationTime
        noteBuilder.deadlineTime = noteDeadlineTime
        noteBuilder.location = noteLocation

        if (selectedNote == null) noteErrors.value = listOf(NoteBuildError.NO_NOTE_SELECTED)

        selectedNote?.let {
            when (val result = noteBuilder.build()) {
                is NoteBuildResult.Success -> {
                    repository.remove(it)
                    repository.add(result.note)
                    clearNoteFields()
                }

                is NoteBuildResult.Failure -> {
                    noteErrors.value = result.errors
                }
            }
        }
    }

    fun getDoneNotes(): SnapshotStateList<Note> = doneRepository.getAll()

    fun getToDoNotes(): SnapshotStateList<Note> = toDoRepository.getAll()

    private fun addToDo(note: Note) = toDoRepository.add(note)
    private fun addDone(note: Note) = doneRepository.add(note)

    fun addToDo() {
        noteBuilder.title.value = noteTitle.value
        noteBuilder.description.value = noteDescription.value
        noteBuilder.notificationTime = noteNotificationTime
        noteBuilder.deadlineTime = noteDeadlineTime
        noteBuilder.location = noteLocation

        when (val result = noteBuilder.build()) {
            is NoteBuildResult.Success -> {
                toDoRepository.add(result.note)
                clearNoteFields()
            }

            is NoteBuildResult.Failure -> {
                noteErrors.value = result.errors
            }
        }
    }

    fun clearNoteFields() {
        noteTitle.value = ""
        noteDescription.value = ""
        noteNotificationTime = null
        noteDeadlineTime = null
        noteErrors.value = emptyList()
    }

    fun doNote(note: Note) {
        deleteToDo(note)
        addDone(note)
    }

    fun undoNote(item: Note) {
        deleteDone(item)
        addToDo(item)
    }
}
