package com.eps.todoturtle.profile.logic

import android.app.Activity
import android.util.Patterns
import android.widget.Toast
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.ui.register.providers.github.GithubKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.tasks.await

class UserAuth(
    private val activity: Activity,
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

    suspend fun loginWithGithub(): Pair<Boolean, String> {
        val provider = OAuthProvider.newBuilder(GithubKeys.PROVIDER.toString())
            .setScopes(listOf(GithubKeys.SCOPE.toString()))
            .build()

        return try {
            auth.startActivityForSignInWithProvider(activity, provider).await()
            true to ""
        } catch (exception: FirebaseAuthException) {
            false to exception.message.toString()
        }
    }

    fun isLoggedIn(): Boolean = auth.currentUser != null

    fun logout() = auth.signOut()

    fun errorToast() {
        val errorMessage = activity.baseContext.getString(R.string.additional_sign_up_error)
        Toast.makeText(activity.baseContext, errorMessage, Toast.LENGTH_SHORT).show()
    }

    fun getActivityContext() = activity.baseContext
    fun getUid() = auth.currentUser!!.uid
}
