package com.orbitmessenger.frontend.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orbitmessenger.frontend.network.UserDto
import com.orbitmessenger.frontend.ui.components.UserAvatar
import com.orbitmessenger.frontend.viewmodel.ChatViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: ChatViewModel,
    onBack: () -> Unit,
    onUserSelected: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    // In a real app we'd use a dedicated SearchViewModel with debounce and Flow,
    // but for simplicity we'll manage local state here.
    
    // We would implement actual search logic calling ChatRepository.
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(50.dp),
                placeholder = { Text("Search users...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                },
                shape = RoundedCornerShape(25.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                ),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Placeholder for search results
            if (query.length > 2) {
                // Here we would observe a flow from the viewmodel
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Searching for '$query'...")
                }
            } else {
                Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.TopCenter) {
                    Text("Type at least 3 characters to search", color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}
