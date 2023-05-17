package com.eps.todoturtle.note.logic

import androidx.compose.runtime.mutableStateOf
import com.eps.todoturtle.shared.logic.forms.Timestamp
import org.osmdroid.util.GeoPoint

class StubNotesViewModel : NotesViewModelInt {
    override var noteTitle = mutableStateOf("")
    override var noteDescription = mutableStateOf("")
    override var noteNotificationTime: Timestamp? = null
    override var noteDeadlineTime: Timestamp? = null
    override var noteLocation: GeoPoint? = null
    override val doneNotes: List<BaseNote>
        get() = emptyList()
    override val toDoNotes: List<BaseNote>
        get() = emptyList()

    override fun addNote() {}

    override fun clearNoteFields() {
        noteTitle.value = ""
        noteDescription.value = ""
        noteNotificationTime = null
        noteDeadlineTime = null
        noteLocation = null
    }

    override fun doNote(note: BaseNote) {}

    override fun undoNote(note: BaseNote) {}
}
