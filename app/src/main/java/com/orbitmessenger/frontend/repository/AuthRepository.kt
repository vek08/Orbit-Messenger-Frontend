package com.orbitmessenger.frontend.repository

import com.orbitmessenger.frontend.network.AuthRequest
import com.orbitmessenger.frontend.network.AuthResponse
import com.orbitmessenger.frontend.network.AuthService
import com.orbitmessenger.frontend.network.RegisterRequest
import com.orbitmessenger.frontend.network.TokenManager

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

class AuthRepository(
    private val authService: AuthService,
    private val tokenManager: TokenManager
) {
    suspend fun login(request: AuthRequest): Result<AuthResponse> {
        return try {
            val response = authService.login(request)
            if (response.isSuccessful) {
                response.body()?.let {
                    tokenManager.saveToken(it.token)
                    Result.Success(it)
                } ?: Result.Error("Empty response body")
            } else {
                // In a real app, parse the error JSON
                val errorMsg = if (response.code() == 401 || response.code() == 403) {
                    "Invalid email or password"
                } else {
                    "Error: ${response.code()} ${response.message()}"
                }
                Result.Error(errorMsg)
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.localizedMessage}")
        }
    }

    suspend fun register(request: RegisterRequest): Result<AuthResponse> {
        return try {
            val response = authService.register(request)
            if (response.isSuccessful) {
                response.body()?.let {
                    tokenManager.saveToken(it.token)
                    Result.Success(it)
                } ?: Result.Error("Empty response body")
            } else {
                val errorMsg = if (response.code() == 400) {
                    "Invalid registration details"
                } else {
                    "Error: ${response.code()} ${response.message()}"
                }
                Result.Error(errorMsg)
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.localizedMessage}")
        }
    }
    
    fun isLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }
    
    fun logout() {
        tokenManager.clearToken()
    }
}
