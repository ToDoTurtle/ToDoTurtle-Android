package com.eps.todoturtle.note.logic

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eps.todoturtle.note.infra.NoteStateRepository
import com.eps.todoturtle.shared.logic.forms.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import org.osmdroid.util.GeoPoint

private const val OFFSET = 100
const val EPS_LAT = 41.608440
const val EPS_LON = 0.623380

class NotesViewModel(
    toDoNotesRepository: NoteRepository,
    doneNotesRepository: NoteRepository
) : ViewModel() {
    private val _toDoNotes = getToDoNotes().toMutableStateList()
    private val _doneNotes = getDoneNotes().toMutableStateList()
    private val noteBuilder = NoteBuilder()
    val noteErrors: MutableStateFlow<Collection<NoteBuildError>> = MutableStateFlow(emptyList())
    var noteTitle = mutableStateOf("")
    var noteDescription = mutableStateOf("")
    var noteNotificationTime: Timestamp? = null
    var noteDeadlineTime: Timestamp? = null
    var noteLocation: GeoPoint? = null

    private val toDoRepository = NoteStateRepository(toDoNotesRepository)
    private val doneRepository = NoteStateRepository(doneNotesRepository)

    companion object {
        val NoteScreenFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                NotesViewModel(
                    toDoNotesRepository = ToDoNotesRepository(),
                    doneNotesRepository = DoneNotesRepository()
                )
            }
        }
    }

    fun addNote() {
        noteBuilder.title.value = noteTitle.value
        noteBuilder.description.value = noteDescription.value
        when (val result = noteBuilder.build()) {
            is NoteBuildResult.Success -> {
                _toDoNotes.add(0, result.note)
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
    }

    val toDoNotes: List<Note>
        get() = _toDoNotes

    val doneNotes: List<Note>
        get() = _doneNotes

    fun doNote(item: Note) {
        _doneNotes.remove(item)
        _toDoNotes.add(0, item)
    }

    fun undoNote(item: Note) {
        _toDoNotes.remove(item)
        _doneNotes.add(0, item)
    }
}

private fun getToDoNotes(): List<Note> =
    List(size = 15) { i ->
        if (i % 2 == 0) {
            Note(
                i,
                title = "Note $i",
                description = "Description of note $i",
            )
        } else {
            Note(
                id = i,
                title = "Note $i",
                description = "Description of note $i",
                location = GeoPoint(EPS_LAT, EPS_LON),
            )
        }
    }

private fun getDoneNotes(): List<Note> =
    List(size = 15) { i ->
        Note(
            id = i + OFFSET,
            title = "Note ${i + OFFSET}",
            description = "Description of note ${i + OFFSET}",
        )
    }

class ToDoNotesRepository : NoteRepository {
    override suspend fun create(note: Note) {
        return
    }

    override suspend fun getAll(): Collection<Note> {
        return emptyList()
    }

    override suspend fun remove(note: Note) {
        return
    }
}

class DoneNotesRepository : NoteRepository {
    override suspend fun create(note: Note) {
        return
    }

    override suspend fun getAll(): Collection<Note> {
        return emptyList()
    }

    override suspend fun remove(note: Note) {
        return
    }
}