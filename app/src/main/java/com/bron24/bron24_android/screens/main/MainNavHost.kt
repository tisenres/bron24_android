package com.bron24.bron24_android.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bron24.bron24_android.screens.main.components.BottomNavigationBar
import com.bron24.bron24_android.screens.home.HomePage
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
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
            AnimatedScreenTransition {
                HomePage(navController)
            }
        }
        composable(Screen.MapPage.route) {
            AnimatedScreenTransition {
                MapPage()
            }
        }
        composable(Screen.OrdersPage.route) {
            AnimatedScreenTransition {
                OrdersPage()
            }
        }
        composable(Screen.ProfilePage.route) {
            AnimatedScreenTransition {
                ProfilePage()
            }
        }
        composable(
            route = Screen.VenueDetails.route,
            arguments = listOf(navArgument("venueId") { type = NavType.IntType }),
//            enterTransition = { slideInVertically(initialOffsetY = { it }) },
//            exitTransition = { slideOutVertically(targetOffsetY = { it }) }
        ) { backStackEntry ->
            val venueId = backStackEntry.arguments?.getInt("venueId") ?: 0
            val viewModel: VenueDetailsViewModel = hiltViewModel()
            AnimatedScreenTransition {
                VenueDetailsScreen(
                    viewModel = viewModel,
                    venueId = venueId,
                    onDismiss = {
                        navController.popBackStack()
                    }
                )
            }

        }
    }
}

@Composable
fun ProfilePage() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Profile Page",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF32B768),
                lineHeight = 30.sp,
                letterSpacing = (-0.028).em
            ),
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}


@Composable
fun MapPage() {
    GoogleMapScreen()
}

@Composable
fun OrdersPage() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Orders Page",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF32B768),
                lineHeight = 30.sp,
                letterSpacing = (-0.028).em
            ),
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}
