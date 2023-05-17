package com.eps.todoturtle.network.logic

import kotlinx.coroutines.flow.MutableStateFlow

interface ConnectionChecker {
    val isNetworkAvailable: MutableStateFlow<Boolean>
}