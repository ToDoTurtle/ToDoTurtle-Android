package com.eps.todoturtle.shared.logic.extensions

import android.graphics.Typeface
import androidx.compose.ui.text.font.FontFamily

private const val ROBOTO_THIN = "Roboto-Thin"

val FontFamily.Companion.RobotoThin: FontFamily
    get() = FontFamily(Typeface.create(ROBOTO_THIN, Typeface.NORMAL))
