package com.eps.todoturtle.network.logic

import kotlinx.coroutines.flow.Flow

interface ConnectionChecker {
    val networkAvailability: Flow<NetworkAvailability>
}