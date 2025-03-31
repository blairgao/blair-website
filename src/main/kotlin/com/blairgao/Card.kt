package com.blairgao

import java.time.LocalDateTime

data class Card(
    val id: Long? = null,
    val name: String,
    val description: String,
    val rarity: CardRarity,
    val imageUrl: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

enum class CardRarity {
    COMMON,
    UNCOMMON,
    RARE,
    EPIC,
    LEGENDARY
} 