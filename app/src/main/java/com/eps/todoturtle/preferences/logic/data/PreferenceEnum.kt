package com.eps.todoturtle.preferences.logic.data

import android.content.Context

interface PreferenceEnum<T> {
    fun enumValues(): Array<T>
    fun getValue(): T
    fun getString(context: Context): String
}