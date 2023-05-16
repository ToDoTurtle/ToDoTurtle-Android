package com.eps.todoturtle.network

import kotlinx.coroutines.flow.MutableStateFlow

interface ConnectionChecker {
    val isNetworkAvailable: MutableStateFlow<Boolean>
}