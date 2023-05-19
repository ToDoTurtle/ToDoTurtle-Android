package com.eps.todoturtle.shared.logic.forms

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState

data class Timestamp(
    var time: Long,
) {
    fun toGoogleTimestamp(): com.google.firebase.Timestamp {
        return com.google.firebase.Timestamp(time / 1000, 0)
    }

    companion object {
        @OptIn(ExperimentalMaterial3Api::class)
        fun fromStates(
            datePickerState: DatePickerState,
            timePickerState: TimePickerState,
        ): Timestamp {
            val date = datePickerState.selectedDateMillis!! +
                    timePickerState.hour * 3_600_000L +
                    timePickerState.minute * 60_000L
            return Timestamp(
                time = date,
            )
        }

        fun fromGoogleTimestamp(timestamp: com.google.firebase.Timestamp?): Timestamp? {
            return timestamp?.let { Timestamp(it.seconds * 1000) }
        }
    }
}