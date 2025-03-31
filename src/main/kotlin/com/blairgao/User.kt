package com.blairgao

import java.time.LocalDateTime

data class User(
    val id: Long? = null,
    val email: String? = null,
    val passwordHash: String? = null,
    val lastLoginAt: LocalDateTime? = null,
    val totalScratches: Int = 0,
    val totalWinnings: Double = 0.0,
    val collectedCards: MutableSet<Card> = mutableSetOf()
) 