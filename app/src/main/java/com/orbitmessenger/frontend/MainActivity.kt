package com.orbitmessenger.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orbitmessenger.frontend.network.ApiClient
import com.orbitmessenger.frontend.network.TokenManager
import com.orbitmessenger.frontend.repository.AuthRepository
import com.orbitmessenger.frontend.repository.ChatRepository
import com.orbitmessenger.frontend.ui.auth.LoginScreen
import com.orbitmessenger.frontend.ui.auth.OnboardingScreen
import com.orbitmessenger.frontend.ui.auth.RegisterScreen
import com.orbitmessenger.frontend.ui.auth.SplashScreen
import com.orbitmessenger.frontend.ui.home.HomeScreen
import com.orbitmessenger.frontend.ui.theme.OrbitMessengerTheme
import com.orbitmessenger.frontend.viewmodel.AuthViewModel
import com.orbitmessenger.frontend.viewmodel.AuthViewModelFactory
import com.orbitmessenger.frontend.viewmodel.ChatViewModel
import com.orbitmessenger.frontend.viewmodel.ChatViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val tokenManager = TokenManager(this)
        val authService = ApiClient.createAuthService(tokenManager)
        val authRepository = AuthRepository(authService, tokenManager)
        val authViewModel = ViewModelProvider(this, AuthViewModelFactory(authRepository))[AuthViewModel::class.java]

        val chatService = ApiClient.createChatService(tokenManager)
        val chatRepository = ChatRepository(chatService)
        val chatViewModel = ViewModelProvider(this, ChatViewModelFactory(chatRepository))[ChatViewModel::class.java]

        setContent {
            OrbitMessengerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OrbitMessengerApp(authViewModel, chatViewModel)
                }
            }
        }
    }
}

@Composable
fun OrbitMessengerApp(authViewModel: AuthViewModel, chatViewModel: ChatViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "splash"
    
    val showBottomNav = currentRoute in listOf("home", "servers", "notifications", "profile")

    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") {
                    SplashScreen(
                        isAuthenticated = authViewModel.authState.value is com.orbitmessenger.frontend.viewmodel.AuthState.Authenticated,
                        onNavigateToHome = {
                            navController.navigate("home") { popUpTo(0) }
                        },
                        onNavigateToOnboarding = {
                            navController.navigate("onboarding") { popUpTo(0) }
                        }
                    )
                }
                composable("onboarding") {
                    OnboardingScreen(
                        onNavigateToLogin = {
                            navController.navigate("login") { popUpTo("onboarding") { inclusive = true } }
                        }
                    )
                }
                composable("login") {
                    LoginScreen(
                        viewModel = authViewModel,
                        onNavigateToRegister = {
                            navController.navigate("register")
                        },
                        onLoginSuccess = {
                            chatViewModel.loadData()
                            navController.navigate("home") { popUpTo(0) }
                        }
                    )
                }
                composable("register") {
                    RegisterScreen(
                        viewModel = authViewModel,
                        onNavigateToLogin = {
                            navController.popBackStack()
                        },
                        onRegisterSuccess = {
                            chatViewModel.loadData()
                            navController.navigate("home") { popUpTo(0) }
                        }
                    )
                }
                composable("home") {
                    HomeScreen(
                        viewModel = chatViewModel,
                        onNavigateToChat = { chatId ->
                            navController.navigate("chat/$chatId")
                        },
                        onNavigate = { route ->
                            if (route != "home") {
                                navController.navigate(route)
                            }
                        }
                    )
                }
                composable("servers") {
                    com.orbitmessenger.frontend.ui.home.PlaceholderScreen(title = "Servers")
                }
                composable("notifications") {
                    com.orbitmessenger.frontend.ui.home.PlaceholderScreen(title = "Notifications")
                }
                composable("profile") {
                    com.orbitmessenger.frontend.ui.home.PlaceholderScreen(title = "Profile")
                }
                composable("search") {
                    com.orbitmessenger.frontend.ui.home.SearchScreen(
                        viewModel = chatViewModel,
                        onBack = { navController.popBackStack() },
                        onUserSelected = { userId ->
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
        
        if (showBottomNav) {
            com.orbitmessenger.frontend.ui.components.OrbitBottomNavigation(
                currentRoute = currentRoute,
                onNavigate = { route -> 
                    if (route != currentRoute) {
                        navController.navigate(route)
                    }
                }
            )
        }
    }
}
