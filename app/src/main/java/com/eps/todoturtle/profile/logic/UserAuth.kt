package com.eps.todoturtle.profile.logic

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth

class UserAuth(
    auth: FirebaseAuth,
) {
    companion object {
        fun invalidMail(mail: String): Boolean =
            !Patterns.EMAIL_ADDRESS.matcher(mail).matches()

        fun invalidPassword(password: String) =
            password.length < 8
    }
}