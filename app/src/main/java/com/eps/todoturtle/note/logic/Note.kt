package com.eps.todoturtle.note.logic

import com.eps.todoturtle.shared.logic.forms.Timestamp
import org.osmdroid.util.GeoPoint

data class Note(
    val identifier: String,
    val title: String,
    val description: String,
    val notificationTime: Timestamp? = null,
    val deadlineTime: Timestamp? = null,
    val location: GeoPoint? = null,
)