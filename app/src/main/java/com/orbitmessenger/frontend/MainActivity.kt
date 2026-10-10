package com.orbitmessenger.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orbitmessenger.frontend.network.ApiClient
import com.orbitmessenger.frontend.network.TokenManager
import com.orbitmessenger.frontend.repository.AuthRepository
import com.orbitmessenger.frontend.ui.auth.LoginScreen
import com.orbitmessenger.frontend.ui.auth.RegisterScreen
import com.orbitmessenger.frontend.ui.theme.OrbitMessengerTheme
import com.orbitmessenger.frontend.viewmodel.AuthViewModel
import com.orbitmessenger.frontend.viewmodel.AuthViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val tokenManager = TokenManager(this)
        val authService = ApiClient.createAuthService(tokenManager)
        val authRepository = AuthRepository(authService, tokenManager)
        val authViewModel = ViewModelProvider(this, AuthViewModelFactory(authRepository))[AuthViewModel::class.java]

        setContent {
            OrbitMessengerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OrbitMessengerApp(authViewModel)
                }
            }
        }
    }
}

@Composable
fun OrbitMessengerApp(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val startDestination = if (authViewModel.authState.value is com.orbitmessenger.frontend.viewmodel.AuthState.Authenticated) {
        "home"
    } else {
        "login"
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = {
                    navController.navigate("register") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo(0) // Clear backstack
                    }
                }
            )
        }
        composable("register") {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onRegisterSuccess = {
                    navController.navigate("home") {
                        popUpTo(0)
                    }
                }
            )
        }
        composable("home") {
            // Placeholder for the main app UI
            Surface(modifier = Modifier.fillMaxSize()) {
                Text("Welcome to Orbit Messenger! Main App UI goes here.")
            }
        }
    }
}
