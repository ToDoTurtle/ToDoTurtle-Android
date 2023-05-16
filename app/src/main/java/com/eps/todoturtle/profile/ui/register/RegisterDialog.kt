package com.eps.todoturtle.profile.ui.register

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.logic.UserAuth
import com.eps.todoturtle.profile.ui.shared.PasswordTextField
import com.eps.todoturtle.profile.ui.shared.UsernameTextField
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch

@Composable
fun RegisterDialog(
    userAuth: UserAuth,
    onDismiss: () -> Unit,
    onSuccessfulRegister: () -> Unit,
) {
    val context = LocalContext.current
    var mail by rememberSaveable { mutableStateOf("") }
    var mailError by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf(context.getString(R.string.sign_up_mail_error)) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(400.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                UsernameTextField(
                    label = R.string.sign_up_mail,
                    errorMessage = errorMessage,
                    username = mail,
                    error = mailError,
                ) {
                    mail = it
                    mailError = false
                }
                PasswordTextField(
                    label = R.string.sign_up_password,
                    errorMessage = stringResource(id = R.string.sign_up_password_error),
                    password = password,
                    error = passwordError,
                ) {
                    password = it
                    passwordError = false
                }
                AdditionalSignUpButton(userAuth) {
                    onDismiss()
                    onSuccessfulRegister()
                }
                val scope = rememberCoroutineScope()
                SignUpButton {
                    if (UserAuth.invalidMail(mail)) {
                        mailError = true
                        return@SignUpButton
                    }
                    if (UserAuth.invalidPassword(password)) {
                        passwordError = true
                        return@SignUpButton
                    }
                    scope.launch {
                        val registerResult = userAuth.registerUser(mail, password)
                        if (registerResult.first) {
                            errorMessage = registerResult.second
                        } else {
                            onDismiss()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SignUpButton(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text(text = stringResource(id = R.string.sign_up_confirm))
    }
}

@Composable
fun AdditionalSignUpButton(userAuth: UserAuth, onSuccessfulRegister: () -> Unit) {
    Text(text = stringResource(id = R.string.addtional_sign_ups))
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val googleLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            it.data?.let {  intent ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                val account = task.result
                val token = account?.idToken
                if (token != null) {
                    scope.launch {
                        val googleResult = userAuth.loginWithGoogle(token)
                        if (!googleResult.first) {
                            Toast.makeText(context, googleResult.second, Toast.LENGTH_SHORT).show()
                        } else {
                            onSuccessfulRegister()
                        }
                    }
                }
                else {
                    Toast.makeText(context, "Error loging in, contact the developers", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(context, userAuth.isLoggedIn().toString(), Toast.LENGTH_SHORT).show()
            }
        }
        Button(onClick = {
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("400657329133-3i6r0oul753t5o80o1e77r8ce9eo0u93.apps.googleusercontent.com")
                .requestEmail()
                .build()
            val intent = GoogleSignIn.getClient(context, options).signInIntent
            googleLauncher.launch(intent)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.google),
                contentDescription = stringResource(
                    id = R.string.google_desc
                ),
                modifier = Modifier.size(24.dp)
            )
        }
        Button(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.github),
                contentDescription = stringResource(
                    id = R.string.github_desc
                ),
                modifier = Modifier.size(24.dp)
            )
        }
        Button(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.yahoo),
                contentDescription = stringResource(
                    id = R.string.yahoo_desc
                ),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
