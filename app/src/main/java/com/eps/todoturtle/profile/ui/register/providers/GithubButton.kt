package com.eps.todoturtle.profile.ui.register.providers

import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.MainActivity
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.logic.UserAuth
import com.google.firebase.auth.OAuthProvider

@Composable
fun GithubButton(
    userAuth: UserAuth,
    onSuccessfulRegister: () -> Unit,
) {
    val context = LocalContext.current
    val provider = OAuthProvider.newBuilder("github.com")
    provider.addCustomParameter("login", "oriolagobat")
    provider.scopes = listOf("user:email")
    userAuth.auth.startActivityForSignInWithProvider(
        userAuth.activity,
        provider.build()
    ).addOnSuccessListener {
        Toast.makeText(context, "Github login success", Toast.LENGTH_SHORT).show()
        onSuccessfulRegister()
    }.addOnFailureListener {
        Toast.makeText(context, "Github login failed", Toast.LENGTH_SHORT).show()
    }
    Button(onClick = {
        Toast.makeText(context, "Github login trigger", Toast.LENGTH_SHORT).show()
    }) {
        Icon(
            painter = painterResource(id = R.drawable.github),
            contentDescription = stringResource(
                id = R.string.github_desc
            ),
            modifier = Modifier.size(24.dp)
        )
    }
}