package com.orbitmessenger.frontend.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.orbitmessenger.frontend.ui.components.OrbitLogo
import com.orbitmessenger.frontend.ui.components.OrbitPasswordField
import com.orbitmessenger.frontend.ui.components.OrbitPrimaryButton
import com.orbitmessenger.frontend.ui.components.OrbitTextField
import com.orbitmessenger.frontend.viewmodel.AuthState
import com.orbitmessenger.frontend.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val authState by viewModel.authState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        
        OrbitLogo(size = 80.dp)
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sign in to continue",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        
        Spacer(modifier = Modifier.height(32.dp))

        OrbitTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            isError = authState is AuthState.Error
        )
        Spacer(modifier = Modifier.height(16.dp))

        OrbitPasswordField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            isError = authState is AuthState.Error
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = { /* Not implemented yet */ }) {
                Text(
                    text = "Forgot Password?",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        if (authState is AuthState.Error) {
            Text(
                text = (authState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        OrbitPrimaryButton(
            text = "Login",
            onClick = { viewModel.login(email, password) },
            isLoading = authState is AuthState.Loading
        )

        Spacer(modifier = Modifier.weight(1f))
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            TextButton(onClick = onNavigateToRegister) {
                Text(
                    text = "Sign Up",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
