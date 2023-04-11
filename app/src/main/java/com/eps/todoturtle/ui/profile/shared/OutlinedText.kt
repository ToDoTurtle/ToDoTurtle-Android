package com.eps.todoturtle.ui.profile.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedText(
    text: MutableState<String>,
    label: Int,
    topPadding: Int,
    isPassword: Boolean = false,
    error: Boolean = false,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier.padding(top = topPadding.dp),
        value = text.value,
        onValueChange = { text.value = it },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        isError = error,
        trailingIcon = {
            if (error) Icon(Icons.Filled.Warning, "Error", tint = MaterialTheme.colorScheme.error)
        },
        label = { Text(stringResource(id = label)) },
    )
}
