package com.eps.todoturtle.profile.ui.login

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.eps.todoturtle.R

@Composable
fun LoginOutlinedText(
    text: MutableState<String>,
    label: Int,
    isPassword: Boolean = false,
    error: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = text.value,
        onValueChange = {
            text.value = it
            error.value = false
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        singleLine = true,
        isError = error.value,
        trailingIcon = {
            if (error.value) {
                Icon(
                    Icons.Filled.Warning,
                    stringResource(id = R.string.profile_error_desc),
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        },
        label = { Text(stringResource(id = label)) },
    )
}
