package com.eps.todoturtle.note.logic

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eps.todoturtle.shared.logic.forms.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import org.osmdroid.util.GeoPoint

private const val OFFSET = 100
const val EPS_LAT = 41.608440
const val EPS_LON = 0.623380

class NotesViewModel(val noteRepository: NoteRepository) : ViewModel(), NotesViewModelInt {
    private val _toDoNotes = getToDoNotes().toMutableStateList()
    private val _doneNotes = getDoneNotes().toMutableStateList()
    private val noteBuilder = NoteBuilder()
    val noteErrors: MutableStateFlow<Collection<NoteBuildError>> = MutableStateFlow(emptyList())
    override var noteTitle = mutableStateOf("")
    override var noteDescription = mutableStateOf("")
    override var noteNotificationTime: Timestamp? = null
    override var noteDeadlineTime: Timestamp? = null
    override var noteLocation: GeoPoint? = null

    companion object {
        val NoteScreenFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                NotesViewModel(
                    noteRepository = NoteScreenNoteRepository(),
                )
            }
        }
    }

    override fun addNote() {
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

    override fun clearNoteFields() {
        noteTitle.value = ""
        noteDescription.value = ""
        noteNotificationTime = null
        noteDeadlineTime = null
    }

    override val toDoNotes: List<BaseNote>
        get() = _toDoNotes

    override val doneNotes: List<BaseNote>
        get() = _doneNotes

    override fun doNote(item: BaseNote) {
        _doneNotes.remove(item)
        _toDoNotes.add(0, item)
    }

    override fun undoNote(item: BaseNote) {
        _toDoNotes.remove(item)
        _doneNotes.add(0, item)
    }
}

private fun getToDoNotes(): List<BaseNote> =
    List(size = 15) { i ->
        if (i % 2 == 0) {
            Note(
                i,
                title = "Note $i",
                description = "Description of note $i",
            )
        } else {
            MapNote(
                id = i,
                title = "Note $i",
                description = "Description of note $i",
                latitude = EPS_LAT,
                longitude = EPS_LON,
            )
        }
    }

private fun getDoneNotes(): List<BaseNote> =
    List(size = 15) { i ->
        Note(
            id = i + OFFSET,
            title = "Note ${i + OFFSET}",
            description = "Description of note ${i + OFFSET}",
        )
    }

abstract class BaseNote(
    open val id: Int,
    open val title: String,
    open val description: String,
)

data class Note(
    override val id: Int,
    override val title: String,
    override val description: String,
    val notificationTime: Timestamp? = null,
    val deadlineTime: Timestamp? = null,
) : BaseNote(id, title, description)

data class MapNote(
    override val id: Int,
    override val title: String,
    override val description: String,
    val latitude: Double,
    val longitude: Double,
) : BaseNote(id, title, description)

interface NoteRepository {
    fun create(note: Note)
    fun search(noteId: Int): Note?
    fun remove(noteId: Int)
}

class NoteScreenNoteRepository : NoteRepository {
    override fun create(note: Note) {
        return
    }

    override fun search(noteId: Int): Note? {
        return null
    }

    override fun remove(noteId: Int) {
        return
    }
}

class DeviceScreenNoteRepository : NoteRepository {
    override fun create(note: Note) {
        return
    }

    override fun search(noteId: Int): Note? {
        return null
    }

    override fun remove(noteId: Int) {
        return
    }
}
