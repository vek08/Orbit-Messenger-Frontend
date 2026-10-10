package com.orbitmessenger.frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.orbitmessenger.frontend.network.ConversationDto
import com.orbitmessenger.frontend.network.UserDto
import com.orbitmessenger.frontend.repository.ChatRepository
import com.orbitmessenger.frontend.repository.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ConversationsState {
    object Loading : ConversationsState()
    data class Success(val conversations: List<ConversationDto>, val currentUser: UserDto?) : ConversationsState()
    data class Error(val message: String) : ConversationsState()
}

class ChatViewModel(private val repository: ChatRepository) : ViewModel() {

    private val _state = MutableStateFlow<ConversationsState>(ConversationsState.Loading)
    val state: StateFlow<ConversationsState> = _state.asStateFlow()
    
    private var currentUser: UserDto? = null

    init {
        loadData()
    }

    fun loadData() {
        _state.value = ConversationsState.Loading
        viewModelScope.launch {
            // Load current user first if needed, or in parallel
            if (currentUser == null) {
                when (val userResult = repository.getCurrentUser()) {
                    is Result.Success -> currentUser = userResult.data
                    is Result.Error -> {} // Ignore for now
                }
            }
            
            when (val result = repository.getConversations()) {
                is Result.Success -> _state.value = ConversationsState.Success(result.data, currentUser)
                is Result.Error -> _state.value = ConversationsState.Error(result.message)
            }
        }
    }
}

class ChatViewModelFactory(private val repository: ChatRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
