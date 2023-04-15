package com.eps.todoturtle.profile.ui.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R

@Composable
fun SignOutProfileButton(
    onSignOutClick: () -> Unit,
) {
    Button(
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 5.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp,
        ),
        onClick = onSignOutClick,
        modifier = Modifier.padding(top = 15.dp),
    ) {
        Text(text = stringResource(id = R.string.sign_out))
    }
}
