package com.eps.todoturtle.shared.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource

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
