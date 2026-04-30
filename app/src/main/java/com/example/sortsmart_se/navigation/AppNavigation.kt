package com.example.sortsmart_se.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sortsmart_se.ui.components.SortSmartBottomBar
import com.example.sortsmart_se.ui.components.bottomNavItems
import com.example.sortsmart_se.ui.screens.*
import com.example.sortsmart_se.viewmodel.WasteViewModel

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("sortsmart_prefs", Context.MODE_PRIVATE)
    val startDest = if (prefs.getBoolean("onboarded", false)) "splash" else "onboarding"

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val viewModel: WasteViewModel = viewModel()

    // Show bottom bar only on specific routes
    val showBottomBar = currentRoute in bottomNavItems.map { it.route } || currentRoute == "searchResults"

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                SortSmartBottomBar(
                    currentRoute = currentRoute ?: "",
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDest,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("onboarding") {
                OnboardingScreen(
                    onFinish = {
                        navController.navigate("splash") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    }
                )
            }
            composable("splash") {
                SplashScreen(
                    onNavigateToHome = {
                        navController.navigate("home") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                )
            }
            
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToSearch = { navController.navigate("searchResults") }
                )
            }
            
            composable("searchResults") {
                SearchResultsScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onItemClick = { item -> navController.navigate("itemDetail/${item.id}") }
                )
            }
            
            composable("itemDetail/{itemId}") { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId")
                
                if (itemId != null) {
                    ItemDetailScreen(
                        itemId = itemId,
                        viewModel = viewModel,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
            
            composable("scan") {
                ScanScreen(
                    onNavigateToSearch = {
                        navController.navigate("searchResults") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    onNavigateToSearchWithQuery = { query ->
                        viewModel.updateSearchQuery(query)
                        navController.navigate("searchResults") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable("tips") {
                TipsScreen()
            }
            composable("profile") {
                ProfileScreen(
                    viewModel = viewModel,
                    onNavigateToFavorites = { navController.navigate("favorites") }
                )
            }
            composable("favorites") {
                FavoritesScreen(
                    viewModel = viewModel,
                    onItemClick = { item -> navController.navigate("itemDetail/${item.id}") }
                )
            }
        }
    }
}
