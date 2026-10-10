package com.orbitmessenger.frontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun OrbitBottomNavigation(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { onNavigate("home") },
            icon = { Icon(Icons.Default.Email, contentDescription = "Chats") },
            label = { Text("Chats") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.surface,
                unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                unselectedTextColor = MaterialTheme.colorScheme.onBackground
            )
        )
        NavigationBarItem(
            selected = currentRoute == "servers",
            onClick = { onNavigate("servers") },
            icon = { Icon(Icons.Default.List, contentDescription = "Servers") },
            label = { Text("Servers") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.surface,
                unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                unselectedTextColor = MaterialTheme.colorScheme.onBackground
            )
        )
        
        // Central Create Button
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                onClick = { /* TODO: Open create menu */ },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                modifier = Modifier.size(48.dp),
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create")
            }
        }

        NavigationBarItem(
            selected = currentRoute == "notifications",
            onClick = { onNavigate("notifications") },
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Notifications") },
            label = { Text("Notifications") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.surface,
                unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                unselectedTextColor = MaterialTheme.colorScheme.onBackground
            )
        )
        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = { onNavigate("profile") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.surface,
                unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                unselectedTextColor = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}
