package com.orbitmessenger.frontend.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    
    @POST("/api/auth/login")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
}
