package com.orbitmessenger.frontend.network

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class UserDto(
    val id: String,
    val username: String,
    val email: String,
    val createdAt: String,
    val lastSeen: String?
)

data class AuthResponse(
    val token: String,
    val user: UserDto
)
