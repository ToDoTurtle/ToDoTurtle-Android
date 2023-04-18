package com.eps.todoturtle.nfc.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay

@Composable
fun ColumnScope.AnimatedDisappearingText(
    modifier: Modifier = Modifier,
    animationDuration: Int = 2000,
    timeShowing: Long = 4000,
    timeDisappearing: Long = 3000,
    text: String,
) {
    Box(modifier = modifier) {
        var visible by rememberSaveable { mutableStateOf(true) }
        this@AnimatedDisappearingText.AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = animationDuration)),
            exit = fadeOut(animationSpec = tween(durationMillis = animationDuration)),
        ) {
            Text(textAlign = TextAlign.Center, text = text)
        }
        LaunchEffect(firstWait = timeShowing, secondWait = timeDisappearing) { visible = !visible }
    }
}

@Composable
private fun LaunchEffect(firstWait: Long = 4000, secondWait: Long = 3000, change: () -> Unit) {
    LaunchedEffect(
        key1 = true,
        block = {
            while (true) {
                delay(firstWait)
                change()
                delay(secondWait)
                change()
            }
        },
    )
}
