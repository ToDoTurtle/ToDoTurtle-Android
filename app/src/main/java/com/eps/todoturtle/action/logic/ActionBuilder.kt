package com.eps.todoturtle.action.logic

import com.eps.todoturtle.shared.logic.extensions.isTooLong
import com.eps.todoturtle.shared.logic.forms.Timestamp

class ActionBuilder {
    var title: String = ""
    var description: String = ""
    var deadline: Timestamp? = null
    var notification: Timestamp? = null
    var getLocation: Boolean = false

    fun build(): NoteActionBuilderResult {
        val errors = mutableListOf<NoteActionBuilderError>()
        if (title.isBlank() || title.isEmpty()) {
            errors.add(NoteActionBuilderError.EMPTY_TITLE)
        }
        if (title.isTooLong()) {
            errors.add(NoteActionBuilderError.TITLE_TOO_LONG)
        }
        if (description.isTooLong()) {
            errors.add(NoteActionBuilderError.DESCRIPTION_TOO_LONG)
        }
        if (errors.isNotEmpty()) return Error(errors)
        return Success(
            NoteAction(
                title,
                description,
                deadline,
                notification,
                getLocation,
            ),
        )
    }

    fun clear() {
        title = ""
        description = ""
        deadline = null
        notification = null
        getLocation = false
    }

    fun loadValuesFromAction(action: NoteAction) {
        with(action) {
            this@ActionBuilder.title = title
            this@ActionBuilder.description = description
            this@ActionBuilder.deadline = deadline
            this@ActionBuilder.notification = notification
            this@ActionBuilder.getLocation = getLocation
        }
    }
}
