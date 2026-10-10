package com.orbitmessenger.frontend.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orbitmessenger.frontend.ui.components.OrbitLogo
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    isAuthenticated: Boolean,
    onNavigateToHome: () -> Unit,
    onNavigateToOnboarding: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        delay(1500)
        if (isAuthenticated) {
            onNavigateToHome()
        } else {
            onNavigateToOnboarding()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OrbitLogo(size = 100.dp)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Orbit Messenger",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Connect. Chat. Together.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
