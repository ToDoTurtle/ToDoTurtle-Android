package com.eps.todoturtle.mock

enum class MockValues(private val value: String) {
    USERNAME("user"),
    PASSWORD("password"),
    ;

    fun getValue(): String {
        return value
    }
}
