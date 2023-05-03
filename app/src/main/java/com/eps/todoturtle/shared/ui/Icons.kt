package com.eps.todoturtle.shared.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.eps.todoturtle.R

@Composable
fun ResourceIcon(
    modifier: Modifier = Modifier,
    @StringRes contentDescriptionId: Int,
    @DrawableRes imageId: Int,
) {
    Icon(
        modifier = modifier,
        imageVector = ImageVector.vectorResource(id = imageId),
        contentDescription = stringResource(id = contentDescriptionId),
    )
}

@Composable
fun ClearTextIcon(
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        ResourceIcon(
            contentDescriptionId = R.string.clear_text_content_desc,
            imageId = R.drawable.clear,
        )
    }
}
