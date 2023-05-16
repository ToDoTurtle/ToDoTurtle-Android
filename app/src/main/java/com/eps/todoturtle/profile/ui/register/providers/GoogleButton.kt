package com.eps.todoturtle.profile.ui.register.providers

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.logic.UserAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch

@Composable
fun GoogleButton(
    userAuth: UserAuth,
    onSuccessfulRegister: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val googleLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            it.data?.let { intent ->
                val token = GoogleSignIn.getSignedInAccountFromIntent(intent).result.idToken
                if (token == null) {
                    userAuth.errorToast(); return@rememberLauncherForActivityResult
                }
                scope.launch {
                    val googleResult = userAuth.loginWithGoogle(token)
                    if (!googleResult.first) {
                        Toast.makeText(context, googleResult.second, Toast.LENGTH_SHORT)
                            .show(); return@launch
                    }
                    onSuccessfulRegister()
                }
            }
        }

    Button(onClick = {
        val clientId = context.getString(R.string.default_web_client_id)
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
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
}