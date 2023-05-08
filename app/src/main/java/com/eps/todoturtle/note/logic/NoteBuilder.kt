package com.eps.todoturtle.note.logic

import androidx.compose.runtime.mutableStateOf
import com.eps.todoturtle.shared.logic.extensions.isTooLong
import com.eps.todoturtle.shared.logic.forms.Timestamp

sealed class NoteBuildResult {
    data class Success(val note: Note) : NoteBuildResult()
    data class Failure(val errors: Collection<NoteBuildError>) : NoteBuildResult()
}

class NoteBuilder {
    var title = mutableStateOf("")
    var description = mutableStateOf("")
    var notificationTime: Timestamp? = null
    var deadlineTime: Timestamp? = null

    fun build(): NoteBuildResult {
        val errors: MutableList<NoteBuildError> = mutableListOf()
        if (title.value.isBlank() || title.value.isEmpty()) {
            errors.add(NoteBuildError.TITLE_EMPTY)
        }
        if (description.value.isTooLong()) {
            errors.add(NoteBuildError.DESCRIPTION_TOO_LONG)
        }
        if (title.value.isTooLong()) {
            errors.add(NoteBuildError.TITLE_TOO_LONG)
        }
        if (errors.isNotEmpty()) return NoteBuildResult.Failure(errors)
        val note =
            Note(
                (0..1000000).random(),
                title.value,
                description.value,
                notificationTime,
                deadlineTime,
            )
        return NoteBuildResult.Success(note)
    }
}
