package com.eps.todoturtle.nfc.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LoadingAnimation(modifier: Modifier = Modifier) {
    var rotateOuter by rememberSaveable {
        mutableStateOf(false)
    }
    val angle by animateFloatAsState(
        targetValue = if (rotateOuter) 360 * 3f else 0f,
        animationSpec = spring(
            visibilityThreshold = 0.3f,
            dampingRatio = 0.1f,
            stiffness = 0.87f,
        ),
    )
    val infiniteTransition = rememberInfiniteTransition()
    val scaleBox by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    LaunchedEffect(
        key1 = true,
        block = {
            rotateOuter = !rotateOuter
            while (true) {
                delay(2000)
                rotateOuter = !rotateOuter
            }
        },
    )
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(1.2f),
        ) {
            Box(
                Modifier
                    .scale(scaleBox)
                    .align(Alignment.Center),
            ) {
                // center circle
                Box(
                    Modifier
                        .align(Alignment.Center)
                        .size(50.dp)
                        .background(colorScheme.inverseSurface, shape = CircleShape),
                )
                // two arc's
                SmallArc(angle)
                BigArc(angle)
            }
        }
    }
}

@Composable
private fun SmallArc(angle: Float) {
    val color = colorScheme.background
    Box(Modifier.rotate(angle)) {
        Canvas(
            modifier = Modifier
                .align(Alignment.Center)
                .size(100.dp),
            onDraw = {
                drawArc(
                    color = color,
                    style = Stroke(
                        width = 3f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round,
                    ),
                    startAngle = 180f,
                    sweepAngle = 288f,
                    useCenter = false,
                )
            },
        )
    }
}

@Composable
private fun BigArc(angle: Float) {
    val color = colorScheme.primary
    Box(Modifier.rotate(angle)) {
        Canvas(
            modifier = Modifier
                .rotate(180f)
                .align(Alignment.Center)
                .size(100.dp),
            onDraw = {
                drawArc(
                    color = color,
                    style = Stroke(
                        width = 3f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round,
                    ),
                    startAngle = 180f,
                    sweepAngle = 288f,
                    useCenter = false,
                )
            },
        )
    }
}
