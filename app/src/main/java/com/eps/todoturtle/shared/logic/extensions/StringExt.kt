package com.eps.todoturtle.shared.logic.extensions

const val MAX_STRING_SIZE = 1000

fun String.isTooLong(): Boolean {
    return this.length > MAX_STRING_SIZE
}
