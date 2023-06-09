package com.eps.todoturtle.profile.ui.shared

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import com.eps.todoturtle.R

@Composable
fun UsernameTextField(
    @StringRes label: Int,
    errorMessage: String,
    username: String,
    error: Boolean,
    onChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = username,
        onValueChange = { onChange(it) },
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        singleLine = true,
        isError = error,
        trailingIcon = {
            if (error) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.form_error),
                    contentDescription = stringResource(id = R.string.profile_error_field),
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        },
        label = { Text(stringResource(id = label)) },
        supportingText = {
            if (error) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
    )
}
