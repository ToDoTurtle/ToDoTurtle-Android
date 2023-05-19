package com.eps.todoturtle.note.logic

import androidx.compose.runtime.mutableStateOf
import com.eps.todoturtle.shared.logic.extensions.isTooLong
import com.eps.todoturtle.shared.logic.forms.Timestamp
import org.osmdroid.util.GeoPoint
import java.util.UUID

sealed class NoteBuildResult {
    data class Success(val note: Note) : NoteBuildResult()
    data class Failure(val errors: List<NoteBuildError>) : NoteBuildResult()
}

class NoteBuilder {
    var title = mutableStateOf("")
    var description = mutableStateOf("")
    var notificationTime: Timestamp? = null
    var deadlineTime: Timestamp? = null
    var location: GeoPoint? = null

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
                UUID.randomUUID().toString(),
                title.value,
                description.value,
                notificationTime,
                deadlineTime,
                location,
            )
        return NoteBuildResult.Success(note)
    }
}
