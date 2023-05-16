package com.eps.todoturtle.profile.logic

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class UserAuth(
    private val auth: FirebaseAuth,
) {
    companion object {
        fun invalidMail(mail: String): Boolean =
            !Patterns.EMAIL_ADDRESS.matcher(mail).matches()

        fun invalidPassword(password: String) =
            password.length < 8
    }

    suspend fun registerUser(mail: String, password: String): Pair<Boolean, String> {
        return try {
            auth.createUserWithEmailAndPassword(mail, password).await()
            true to ""
        } catch (exception: FirebaseAuthException) {
            false to exception.message.toString()
        }
    }

    suspend fun login(mail: String, password: String): Pair<Boolean, String> {
        return try {
            auth.signInWithEmailAndPassword(mail, password).await()
            true to ""
        } catch (exception: FirebaseAuthException) {
            false to exception.message.toString()
        }
    }

    suspend fun loginWithGoogle(token: String): Pair<Boolean, String> {
        val credential = GoogleAuthProvider.getCredential(token, null)
        return try {
            auth.signInWithCredential(credential).await()
            true to ""
        } catch (exception: FirebaseAuthException) {
            false to exception.message.toString()
        }
    }

    fun isLoggedIn(): Boolean = auth.currentUser != null

    fun logout() = auth.signOut()
}
