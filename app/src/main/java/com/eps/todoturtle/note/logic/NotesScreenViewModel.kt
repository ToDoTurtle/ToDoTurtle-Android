package com.eps.todoturtle.note.logic

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

private const val OFFSET = 100
private const val EPS_LAT  = 41.608440
private const val EPS_LON = 0.623380

class NoteScreenViewModel : ViewModel() {
    private val _toDoNotes = getToDoNotes().toMutableStateList()
    private val _doneNotes = getDoneNotes().toMutableStateList()

    val toDoNotes: List<BaseNote>
        get() = _toDoNotes

    val doneNotes: List<BaseNote>
        get() = _doneNotes

    fun doNote(item: BaseNote) {
        _doneNotes.remove(item)
        _toDoNotes.add(0, item)
    }

    fun undoNote(item: BaseNote) {
        _toDoNotes.remove(item)
        _doneNotes.add(0, item)
    }
}

private fun getToDoNotes(): List<BaseNote> =
    List(size = 15) { i ->
        if (i % 2 == 0) {
            Note(i, "Note # $i", "This is the description of Note # $i")
        }
        else {
            MapNote(
                id = i,
                title = "Note # $i",
                description = "This is the description of Note # $i",
                latitude = EPS_LAT,
                longitude = EPS_LON,
            )
        }
    }

private fun getDoneNotes(): List<BaseNote> =
    List(size = 15) { i ->
        Note(
            id = i + OFFSET,
            title = "Note # ${i + OFFSET}",
            description = "This is the description of Note # ${i + OFFSET}",
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
) : BaseNote(id, title, description)

data class MapNote(
    override val id: Int,
    override val title: String,
    override val description: String,
    val latitude: Double,
    val longitude: Double,
) : BaseNote(id, title, description)