package com.orbitmessenger.frontend.network

data class MessageDto(
    val id: String,
    val conversationId: String,
    val sender: UserDto,
    val content: String,
    val createdAt: String
)

data class ConversationDto(
    val id: String,
    val isGroup: Boolean,
    val name: String?,
    val createdAt: String,
    val members: List<UserDto>,
    val latestMessage: MessageDto?,
    val unreadCount: Int
)

data class CreateDirectRequest(
    val targetUserId: String
)

data class CreateGroupRequest(
    val name: String,
    val memberIds: List<String>
)
