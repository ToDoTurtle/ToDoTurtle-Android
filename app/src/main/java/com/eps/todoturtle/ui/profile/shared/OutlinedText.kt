package com.eps.todoturtle.ui.profile.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedText(
    username: MutableState<String>,
    label: Int,
    topPadding: Int,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier.padding(top = topPadding.dp),
        value = username.value,
        onValueChange = { username.value = it },
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        label = { Text(stringResource(id = label)) },
    )
}
