package com.eps.todoturtle.profile.ui.register.providers

enum class GithubKeys(private val value: String) {
    PROVIDER("github.com"),
    LOGIN_PARAM("login"),
    SCOPE("user:email"),
    ;

    override fun toString(): String {
        return value
    }
}