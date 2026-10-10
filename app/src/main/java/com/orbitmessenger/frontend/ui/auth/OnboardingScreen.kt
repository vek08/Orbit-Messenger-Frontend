package com.orbitmessenger.frontend.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.orbitmessenger.frontend.ui.components.OrbitLogo
import com.orbitmessenger.frontend.ui.components.OrbitPrimaryButton

@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        
        // Mock Illustration (using Logo as placeholder)
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            OrbitLogo(size = 120.dp)
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = "Chat. Collaborate.\nGrow Together.",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Real-time messaging for friends,\nteams and communities.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Page indicator dots
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(MaterialTheme.colorScheme.outline))
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(MaterialTheme.colorScheme.outline))
        }

        Spacer(modifier = Modifier.weight(1f))
        
        OrbitPrimaryButton(
            text = "Get Started",
            onClick = onNavigateToLogin
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
