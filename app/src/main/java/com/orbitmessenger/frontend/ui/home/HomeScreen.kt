package com.orbitmessenger.frontend.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.orbitmessenger.frontend.network.ConversationDto
import com.orbitmessenger.frontend.ui.components.UserAvatar
import com.orbitmessenger.frontend.viewmodel.ChatViewModel
import com.orbitmessenger.frontend.viewmodel.ConversationsState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ChatViewModel,
    onNavigateToChat: (String) -> Unit,
    onNavigate: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val currentState = state
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Chats",
                    style = MaterialTheme.typography.headlineLarge
                )
                Row {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            }

            // Search Bar Placeholder
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                onClick = { onNavigate("search") }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Search chats, users or groups...", color = MaterialTheme.colorScheme.secondary)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Online Contacts Row
            if (currentState is ConversationsState.Success) {
                // Extract unique members from conversations for mock "online" users
                val onlineUsers = currentState.conversations
                    .flatMap { it.members }
                    .filter { it.id != currentState.currentUser?.id }
                    .distinctBy { it.id }
                    .take(8)
                
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            UserAvatar(name = "Your Story", size = 56.dp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Your Story", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
                        }
                    }
                    items(onlineUsers) { user ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            UserAvatar(name = user.username, isOnline = true, size = 56.dp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(user.username, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Filter Chips
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val filters = listOf("All", "Direct", "Groups", "Servers")
                items(filters) { filter ->
                    val isSelected = filter == "All"
                    FilterChip(
                        selected = isSelected,
                        onClick = { /* TODO */ },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Chat List
            when (currentState) {
                is ConversationsState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is ConversationsState.Error -> {
                    Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                        Text(currentState.message, color = MaterialTheme.colorScheme.error)
                    }
                }
                is ConversationsState.Success -> {
                    if (currentState.conversations.isEmpty()) {
                        Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                            Text("No conversations yet", color = MaterialTheme.colorScheme.secondary)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().weight(1f)
                        ) {
                            items(currentState.conversations) { conversation ->
                                ConversationItem(
                                    conversation = conversation,
                                    currentUserId = currentState.currentUser?.id,
                                    onClick = { onNavigateToChat(conversation.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConversationItem(
    conversation: ConversationDto,
    currentUserId: String?,
    onClick: () -> Unit
) {
    val displayName = if (conversation.isGroup) {
        conversation.name ?: "Group Chat"
    } else {
        conversation.members.firstOrNull { it.id != currentUserId }?.username ?: "Unknown User"
    }
    
    val lastMessageText = conversation.latestMessage?.content ?: "No messages yet"
    val time = conversation.latestMessage?.createdAt?.let {
        // Very basic time formatting for the mock
        try {
            val dateTime = LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME)
            "${dateTime.hour.toString().padStart(2, '0')}:${dateTime.minute.toString().padStart(2, '0')}"
        } catch (e: Exception) {
            "..."
        }
    } ?: ""

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserAvatar(name = displayName, isOnline = !conversation.isGroup) // Mock online status
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = displayName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = lastMessageText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = time,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            if (conversation.unreadCount > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = conversation.unreadCount.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}
