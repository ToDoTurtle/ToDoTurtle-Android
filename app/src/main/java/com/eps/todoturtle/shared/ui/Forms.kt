package com.eps.todoturtle.shared.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun FormOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes labelId: Int,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    onValueChange: (String) -> Unit = {},
    hasError: Boolean = false,
    errorMessage: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        singleLine = singleLine,
        maxLines = maxLines,
        onValueChange = { onValueChange(it) },
        isError = hasError,
        label = { Text(stringResource(id = labelId)) },
        supportingText = {
            if (hasError) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        trailingIcon = { if (value.isNotEmpty()) trailingIcon?.invoke() },
    )
}

@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes labelId: Int,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    onValueChange: (String) -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        singleLine = singleLine,
        maxLines = maxLines,
        onValueChange = { onValueChange(it) },
        label = { Text(stringResource(id = labelId)) },
        trailingIcon = { if (value.isNotEmpty()) trailingIcon?.invoke() },
    )
}
