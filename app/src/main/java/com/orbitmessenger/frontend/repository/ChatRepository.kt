package com.orbitmessenger.frontend.repository

import com.orbitmessenger.frontend.network.ChatService
import com.orbitmessenger.frontend.network.ConversationDto
import com.orbitmessenger.frontend.network.CreateDirectRequest
import com.orbitmessenger.frontend.network.UserDto

class ChatRepository(private val chatService: ChatService) {

    suspend fun getConversations(): Result<List<ConversationDto>> {
        return try {
            val response = chatService.getConversations()
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error("Failed to load conversations: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Unknown network error")
        }
    }

    suspend fun searchUsers(query: String): Result<List<UserDto>> {
        return try {
            val response = chatService.searchUsers(query)
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error("Failed to search users")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun createDirectConversation(userId: String): Result<ConversationDto> {
        return try {
            val response = chatService.createDirectConversation(CreateDirectRequest(userId))
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error("Empty response")
            } else {
                Result.Error("Failed to create conversation")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Unknown error")
        }
    }
    
    suspend fun getCurrentUser(): Result<UserDto> {
        return try {
            val response = chatService.getCurrentUser()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error("Empty response")
            } else {
                Result.Error("Failed to get current user")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}
