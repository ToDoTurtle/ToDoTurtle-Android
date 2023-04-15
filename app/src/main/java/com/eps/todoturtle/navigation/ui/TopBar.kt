package com.eps.todoturtle.navigation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.shared.logic.extensions.bitmapFrom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    shouldShowMenu: Boolean = true,
    onMenuClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            AppTitle()
        },
        navigationIcon = {
            if (shouldShowMenu) {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Open drawer menu",
                    )
                }
            }
        },
    )
}

@Composable
fun AppTitle() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.weight(0.25f))
        Text(
            modifier = Modifier.weight(0.6f),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Black,
            ),
            text = stringResource(R.string.app_title),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Image(
            bitmap = bitmapFrom(R.drawable.turtle, LocalContext.current).asImageBitmap(),
            contentDescription = "Turtle",
            modifier = Modifier
                .scale(0.4f)
                .offset(y = (8).dp, x = (-65).dp)
                .weight(0.3f),
        )
    }
}
