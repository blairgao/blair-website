package com.blairgao

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody request: RegisterRequest): ResponseEntity<User> {
        return try {
            val user = userService.registerUser(request.email, request.password)
            ResponseEntity.ok(user)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody request: LoginRequest): ResponseEntity<User> {
        return try {
            val user = userService.loginUser(request.email, request.password)
            ResponseEntity.ok(user)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/{userId}")
    fun getUserProfile(@PathVariable userId: Long): ResponseEntity<User> {
        return try {
            val user = userService.getUserProfile(userId)
            ResponseEntity.ok(user)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{userId}")
    fun updateUserProfile(
        @PathVariable userId: Long,
        @RequestBody request: UpdateProfileRequest
    ): ResponseEntity<User> {
        return try {
            val user = userService.updateUserProfile(userId, request.email)
            ResponseEntity.ok(user)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PutMapping("/{userId}/stats")
    fun updateUserStats(
        @PathVariable userId: Long,
        @RequestBody request: UpdateStatsRequest
    ): ResponseEntity<User> {
        return try {
            userService.updateUserStats(userId, request.winnings)
            val user = userService.getUserProfile(userId)
            ResponseEntity.ok(user)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/{userId}/cards")
    fun addCardToCollection(
        @PathVariable userId: Long,
        @RequestBody card: Card
    ): ResponseEntity<User> {
        return try {
            val user = userService.addCardToCollection(userId, card)
            ResponseEntity.ok(user)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/{userId}/cards")
    fun getCollectedCards(@PathVariable userId: Long): ResponseEntity<Set<Card>> {
        return try {
            val cards = userService.getCollectedCards(userId)
            ResponseEntity.ok(cards)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/{userId}/cards/{cardId}")
    fun hasCard(
        @PathVariable userId: Long,
        @PathVariable cardId: Long
    ): ResponseEntity<Boolean> {
        return try {
            val hasCard = userService.hasCard(userId, cardId)
            ResponseEntity.ok(hasCard)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }
}

data class RegisterRequest(
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class UpdateProfileRequest(
    val email: String
)

data class UpdateStatsRequest(
    val winnings: Double
) 