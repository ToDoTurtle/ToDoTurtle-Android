package com.eps.todoturtle.profile.ui.login

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.eps.todoturtle.R

@Composable
fun LoginOutlinedText(
    text: MutableState<String>,
    label: Int,
    error: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = text.value,
        onValueChange = {
            text.value = it
            error.value = false
        },
        visualTransformation = VisualTransformation.None,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        singleLine = true,
        isError = error.value,
        trailingIcon = {
            if (error.value) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.form_error),
                    "Error",
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        },
        label = { Text(stringResource(id = label)) },
    )
}

@Composable
fun LoginOutlinedPassword(
    text: MutableState<String>,
    label: Int,
    errorLabel: String? = null,
    error: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = text.value,
        onValueChange = {
            text.value = it
            error.value = false
        },
        visualTransformation = PasswordVisualTransformation(),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
        ),
        singleLine = true,
        isError = error.value,
        trailingIcon = {
            if (error.value) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.form_error),
                    "Error",
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        },
        supportingText = {
            if (error.value) {
                Text(
                    text = errorLabel ?: stringResource(id = R.string.login_wrong_username_or_password),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        label = { Text(stringResource(id = label)) },
    )
}
