package com.orbitmessenger.frontend.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    // 10.0.2.2 points to the host machine's localhost from the Android emulator
    private const val BASE_URL = "http://192.168.84.36:8080/"

    fun createAuthService(tokenManager: TokenManager): AuthService {
        val authInterceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            
            tokenManager.getToken()?.let { token ->
                requestBuilder.header("Authorization", "Bearer $token")
            }
            
            chain.proceed(requestBuilder.build())
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    fun createChatService(tokenManager: TokenManager): ChatService {
        val authInterceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            
            tokenManager.getToken()?.let { token ->
                requestBuilder.header("Authorization", "Bearer $token")
            }
            
            chain.proceed(requestBuilder.build())
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatService::class.java)
    }
}
