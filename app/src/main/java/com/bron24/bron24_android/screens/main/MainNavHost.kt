package com.bron24.bron24_android.screens.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.bron24.bron24_android.screens.main.components.SplashScreen
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.map.GoogleMapScreen
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsScreen
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsViewModel

@Composable
fun MainAppScaffold() {
    val nestedNavController = rememberNavController()

    val shouldShowBottomBar = remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar.value) {
                BottomNavigationBar(navController = nestedNavController)
            }
        }
    ) { paddingValues ->
        MainNavHost(
            navController = nestedNavController,
            modifier = Modifier.padding(paddingValues),
            onDestinationChanged = { destination ->
                shouldShowBottomBar.value = destination != Screen.VenueDetails.route
            }
        )
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier,
    onDestinationChanged: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomePage.route,
        modifier = modifier,
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    durationMillis = 300, easing = LinearEasing
                )
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    durationMillis = 300, easing = LinearEasing
                )
            )
        },
        popEnterTransition = {
            fadeIn(
                animationSpec = tween(
                    durationMillis = 300, easing = LinearEasing
                )
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(
                    durationMillis = 300, easing = LinearEasing
                )
            )
        }
    ) {
        composable(Screen.HomePage.route) {
            onDestinationChanged(Screen.HomePage.route)
            HomePage(navController)
        }
        composable(Screen.MapPage.route) {
            onDestinationChanged(Screen.MapPage.route)
            MapPage()
        }
        composable(Screen.OrdersPage.route) {
            onDestinationChanged(Screen.OrdersPage.route)
            OrdersPage()
        }
        composable(Screen.ProfilePage.route) {
            onDestinationChanged(Screen.ProfilePage.route)
            ProfilePage()
        }
        composable(
            route = Screen.VenueDetails.route,
            arguments = listOf(navArgument("venueId") { type = NavType.IntType }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(100, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(100, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { backStackEntry ->
            onDestinationChanged(Screen.VenueDetails.route)
            val venueId = backStackEntry.arguments?.getInt("venueId") ?: 0
            val viewModel: VenueDetailsViewModel = hiltViewModel()
            VenueDetailsScreen(
                viewModel = viewModel,
                venueId = venueId,
                onBackClick = {
                    navController.popBackStack()
                },
            )
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