package com.eps.todoturtle.note.logic

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

private const val OFFSET = 100

class NoteScreenViewModel : ViewModel() {
    private val _toDoNotes = getToDoNotes().toMutableStateList()
    private val _doneNotes = getDoneNotes().toMutableStateList()

    val toDoNotes: List<Note>
        get() = _toDoNotes

    val doneNotes: List<Note>
        get() = _doneNotes

    fun doNote(item: Note) {
        _doneNotes.remove(item)
        _toDoNotes.add(item)
    }

    fun undoNote(item: Note) {
        _toDoNotes.remove(item)
        _doneNotes.add(item)
    }
}

private fun getToDoNotes() =
    List(size = 15) { i -> Note(i, "Note # $i", "This is the description of Note # $i") }

private fun getDoneNotes() =
    List(size = 15) { i ->
        Note(
            id = i + OFFSET,
            title = "Note # ${i + OFFSET}",
            description = "This is the description of Note # ${i + OFFSET}",
        )
    }

data class Note(
    val id: Int,
    val title: String,
    val description: String,
)
