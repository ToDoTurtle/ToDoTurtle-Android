package com.eps.todoturtle.shared.logic.forms

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState

data class Timestamp(
    val time: Long,
) {
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
    }
}
