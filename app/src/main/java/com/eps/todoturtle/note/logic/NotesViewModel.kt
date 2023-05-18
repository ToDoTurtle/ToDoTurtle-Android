package com.eps.todoturtle.note.logic

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eps.todoturtle.note.infra.NoteStateRepository
import com.eps.todoturtle.shared.logic.forms.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import org.osmdroid.util.GeoPoint

private const val OFFSET = 100
const val EPS_LAT = 41.608440
const val EPS_LON = 0.623380

class NotesViewModel(
    toDoNotesRepository: NoteRepository,
    doneNotesRepository: NoteRepository
) : ViewModel() {
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

    fun deleteDone(note: Note) = doneRepository.remove(note)

    fun deleteToDo(note: Note) = toDoRepository.remove(note)

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

class ToDoNotesRepository : NoteRepository {
    override suspend fun add(note: Note) {
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
    override suspend fun add(note: Note) {
        return
    }

    override suspend fun getAll(): Collection<Note> {
        return emptyList()
    }

    override suspend fun remove(note: Note) {
        return
    }
}