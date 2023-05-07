package com.eps.todoturtle.profile.logic

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.gestures.rememberTransformableState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class UserAuth(
    private val context: Context,
    private val auth: FirebaseAuth,
) {
    companion object {
        fun invalidMail(mail: String): Boolean =
            !Patterns.EMAIL_ADDRESS.matcher(mail).matches()

        fun invalidPassword(password: String) =
            password.length < 8
    }

    fun registerUser(mail: String, password: String) {
        auth.createUserWithEmailAndPassword(mail, password).addOnFailureListener {
            Toast.makeText(
                context,
                it.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    suspend fun login(mail: String, password: String): Pair<Boolean, String> {
        return try {
            auth.signInWithEmailAndPassword(mail, password).await()
            return true to ""
        } catch (exception: Exception) {
            false to exception.message.toString()
        }
    }

    fun isLoggedIn(): Boolean = auth.currentUser != null

    fun logout() = auth.signOut()
}