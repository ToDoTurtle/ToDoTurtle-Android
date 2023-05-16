package com.eps.todoturtle.note.logic

import androidx.compose.runtime.MutableState
import com.eps.todoturtle.shared.logic.forms.Timestamp

interface NotesViewModelInt {
    var noteTitle: MutableState<String>
    var noteDescription: MutableState<String>
    var noteNotificationTime: Timestamp?
    var noteDeadlineTime: Timestamp?
    var noteLocation: Location?
    val doneNotes: List<BaseNote>
    val toDoNotes: List<BaseNote>

    fun addNote()
    fun clearNoteFields()
    fun doNote(note: BaseNote)
    fun undoNote(note: BaseNote)
}
