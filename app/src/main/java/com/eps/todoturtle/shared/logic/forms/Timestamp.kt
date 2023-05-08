package com.eps.todoturtle.shared.logic.forms

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState

data class Timestamp(
    val date: Long,
    val hour: Int,
    val minute: Int,
) {
    companion object {
        @OptIn(ExperimentalMaterial3Api::class)
        fun fromStates(
            datePickerState: DatePickerState,
            timePickerState: TimePickerState,
        ): Timestamp {
            return Timestamp(
                date = datePickerState.selectedDateMillis!!,
                hour = timePickerState.hour,
                minute = timePickerState.minute,
            )
        }
    }
}
