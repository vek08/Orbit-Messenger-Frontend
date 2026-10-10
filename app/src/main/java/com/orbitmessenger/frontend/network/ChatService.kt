package com.orbitmessenger.frontend.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatService {
    @GET("/api/conversations")
    suspend fun getConversations(): Response<List<ConversationDto>>

    @POST("/api/conversations/direct")
    suspend fun createDirectConversation(@Body request: CreateDirectRequest): Response<ConversationDto>

    @POST("/api/conversations/group")
    suspend fun createGroupConversation(@Body request: CreateGroupRequest): Response<ConversationDto>

    @POST("/api/conversations/{conversationId}/read")
    suspend fun markAsRead(@Path("conversationId") conversationId: String): Response<Void>
    
    @GET("/api/users/search")
    suspend fun searchUsers(@Query("query") query: String): Response<List<UserDto>>
    
    @GET("/api/users/me")
    suspend fun getCurrentUser(): Response<UserDto>
}
