package com.eps.todoturtle.profile.ui.register.providers.github

enum class GithubKeys(private val value: String) {
    PROVIDER("github.com"),
    SCOPE("user:email"),
    ;

    override fun toString(): String {
        return value
    }
}
