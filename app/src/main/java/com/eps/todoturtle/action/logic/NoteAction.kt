package com.eps.todoturtle.action.logic

import com.eps.todoturtle.shared.logic.forms.Timestamp

data class NoteAction(
    val title: String,
    val description: String,
    val deadline: Timestamp?,
    val notification: Timestamp?,
    val getLocation: Boolean
)