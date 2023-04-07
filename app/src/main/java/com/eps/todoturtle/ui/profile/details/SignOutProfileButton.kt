package com.eps.todoturtle.ui.profile.details

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R

@Composable
fun SignOutProfileButton() {
    val context = LocalContext.current

    Button(onClick = {
        Toast.makeText(context, "TODO: Sign out", Toast.LENGTH_SHORT).show()
    }, modifier = Modifier.padding(top = 15.dp)) {
        Text(text = stringResource(id = R.string.sign_out))
    }
}
