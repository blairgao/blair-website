package com.blairgao

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService {
    private val users = mutableMapOf<Long, User>()
    private var nextId = 1L
    private var nextCardId = 1L

    //todo: add password hashing
    fun registerUser(email: String, password: String): User {
        // Check if user already exists
        if (users.values.any { it.email == email }) {
            throw IllegalArgumentException("Email already registered")
        }

        val user = User(
            id = nextId++,
            email = email,
            passwordHash = password
        )
        users[user.id!!] = user
        return user
    }

    fun loginUser(email: String, password: String): User {
        val user = users.values.find { it.email == email }
            ?: throw IllegalArgumentException("User not found")

        if (password != user.passwordHash) {
            throw IllegalArgumentException("Invalid password")
        }

        // Update last login time
        val updatedUser = user.copy(lastLoginAt = LocalDateTime.now())
        users[user.id!!] = updatedUser
        return updatedUser
    }

    fun getUserProfile(userId: Long): User {
        return users[userId] ?: throw IllegalArgumentException("User not found")
    }

    fun updateUserProfile(userId: Long, email: String): User {
        val user = users[userId] ?: throw IllegalArgumentException("User not found")
        
        // Check if new email is already taken
        if (email != user.email && users.values.any { it.email == email }) {
            throw IllegalArgumentException("Email already taken")
        }

        val updatedUser = user.copy(email = email)
        users[user.id!!] = updatedUser
        return updatedUser
    }

    fun updateUserStats(userId: Long, winnings: Double) {
        val user = users[userId] ?: throw IllegalArgumentException("User not found")
        val updatedUser = user.copy(
            totalScratches = user.totalScratches + 1,
            totalWinnings = user.totalWinnings + winnings
        )
        users[user.id!!] = updatedUser
    }

    fun addCardToCollection(userId: Long, card: Card): User {
        val user = users[userId] ?: throw IllegalArgumentException("User not found")
        val cardWithId = card.copy(id = nextCardId++)
        val updatedUser = user.copy(collectedCards = (user.collectedCards + cardWithId).toMutableSet())
        users[user.id!!] = updatedUser
        return updatedUser
    }

    fun getCollectedCards(userId: Long): Set<Card> {
        val user = users[userId] ?: throw IllegalArgumentException("User not found")
        return user.collectedCards
    }

    fun hasCard(userId: Long, cardId: Long): Boolean {
        val user = users[userId] ?: throw IllegalArgumentException("User not found")
        return user.collectedCards.any { it.id == cardId }
    }
} 