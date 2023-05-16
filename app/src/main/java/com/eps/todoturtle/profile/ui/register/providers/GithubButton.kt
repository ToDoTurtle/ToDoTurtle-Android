package com.eps.todoturtle.profile.ui.register.providers

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.logic.UserAuth
import com.eps.todoturtle.profile.ui.shared.InputTextField
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.launch

@Composable
fun GithubButton(
    userAuth: UserAuth,
    onSuccessfulRegister: () -> Unit,
) {
    var shouldShowGithubDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Button(onClick = {
        shouldShowGithubDialog = true
    }) {
        Icon(
            painter = painterResource(id = R.drawable.github),
            contentDescription = stringResource(
                id = R.string.github_desc
            ),
            modifier = Modifier.size(24.dp)
        )
    }

    if (shouldShowGithubDialog) {
        GithubUsernameDialog(
            onDismiss = { shouldShowGithubDialog = false },
        ) {
            scope.launch {
                val loginResult = userAuth.loginWithGithub("oriolagobat")
                if (loginResult.first) {
                    onSuccessfulRegister()
                } else {
                    Toast.makeText(
                        context,
                        loginResult.second,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

@Composable
fun GithubUsernameDialog(
    onDismiss: () -> Unit,
    onClick: () -> Unit,
) {
    var username by rememberSaveable { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                InputTextField(value = username, label = R.string.github_username) {
                    username = it
                }
                Button(onClick = {
                    onClick()
                }) {
                    Text(text = stringResource(id = R.string.sign_up_confirm))
                }
            }
        }
    }
}