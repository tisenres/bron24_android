package com.bron24.bron24_android.screens.main

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bron24.bron24_android.screens.main.components.BottomNavigationBar
import com.bron24.bron24_android.screens.home.HomePage
import com.bron24.bron24_android.screens.map.GoogleMapScreen
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsScreen
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsViewModel

@Composable
fun MainAppScaffold() {
    val nestedNavController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = nestedNavController) }
    ) { paddingValues ->
        MainNavHost(navController = nestedNavController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomePage.route,
        modifier = modifier
    ) {
        composable(Screen.HomePage.route) {
            HomePage(navController)
        }
        composable(Screen.MapPage.route) {
            MapPage()
        }
        composable(Screen.CartPage.route) {
            CartPage()
        }
        composable(Screen.ProfilePage.route) {
            ProfilePage()
        }
        composable(
            route = Screen.VenueDetails.route,
            arguments = listOf(navArgument("venueId") { type = NavType.IntType }),
            enterTransition = { slideInVertically(initialOffsetY = { it }) },
            exitTransition = { slideOutVertically(targetOffsetY = { it }) }
        ) { backStackEntry ->
            val venueId = backStackEntry.arguments?.getInt("venueId") ?: 0
            val viewModel: VenueDetailsViewModel = hiltViewModel()
            VenueDetailsScreen(
                viewModel = viewModel,
                venueId = venueId,
                onDismiss = {
                    navController.popBackStack() // Navigate back when dismissed
                })
        }
    }
}

@Composable
fun ProfilePage() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Text("Profile Page Content")
    }
}


@Composable
fun MapPage() {
    GoogleMapScreen()
}

@Composable
fun CartPage() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Text("Cart Page Content")
    }
}
