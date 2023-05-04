package com.eps.todoturtle.profile.ui.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.logic.UserAuth
import com.eps.todoturtle.profile.ui.shared.PasswordTextField
import com.eps.todoturtle.profile.ui.shared.UsernameTextField

@Composable
fun SignUpDialog(
    userAuth: UserAuth,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    var mail by rememberSaveable { mutableStateOf("") }
    var mailError by rememberSaveable { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier.size(300.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                UsernameTextField(
                    label = R.string.sign_up_mail,
                    errorMessage = R.string.sign_up_mail_error,
                    username = mail,
                    error = mailError,
                ) {
                    mail = it
                    mailError = false
                }
                PasswordTextField(
                    label = R.string.sign_up_password,
                    errorMessage = R.string.sign_up_password_error,
                    password = password,
                    error = passwordError,
                ) {
                    password = it
                    passwordError = false
                }
                SignUpButton {
                    if (UserAuth.invalidMail(mail)) {
                        mailError = true
                        return@SignUpButton
                    }
                    if (UserAuth.invalidPassword(password)) {
                        passwordError = true
                        return@SignUpButton
                    }
                    userAuth.registerUser(mail, password)
                    onDismiss()
                }
            }
        }
    }
}

@Composable
fun SignUpButton(onClick: () -> Unit) {
    Button(onClick = {onClick()}) {
        Text(text = stringResource(id = R.string.sign_up_confirm))
    }
}