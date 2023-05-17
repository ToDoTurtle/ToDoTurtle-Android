package com.eps.todoturtle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eps.todoturtle.profile.logic.UserAuth
import com.google.firebase.auth.FirebaseAuth

class InitialActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        val userAuth = UserAuth(this@InitialActivity, auth)

        intent = if (!userAuth.isLoggedIn()) {
            Intent(this, LoginActivity::class.java)
        } else {
            Intent(this, MainActivity::class.java)
        }
        startActivity(intent)
    }
}