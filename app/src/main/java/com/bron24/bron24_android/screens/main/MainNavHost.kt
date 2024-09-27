package com.bron24.bron24_android.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bron24.bron24_android.helper.util.presentation.components.BottomNavigationBar
import com.bron24.bron24_android.screens.booking.screens.BookingScreen
import com.bron24.bron24_android.screens.booking.BookingViewModel
import com.bron24.bron24_android.screens.home.HomePage
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.map.YandexMapScreen
import com.bron24.bron24_android.screens.searchfilter.FilterScreen
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsScreen
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier,
    onDestinationChanged: (String) -> Unit
) {

    var showBookingBottomSheet by remember { mutableStateOf(false) }
    val (currentPricePerHour, setPricePerHour) = remember { mutableStateOf("") }
    val (currentSectors, setSectors) = remember { mutableStateOf(emptyList<String>()) }

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

        composable(
            route = Screen.MapPage.route,
            arguments = listOf(
                navArgument("latitude") { type = NavType.FloatType; defaultValue = 0f },
                navArgument("longitude") { type = NavType.FloatType; defaultValue = 0f },
                navArgument("selectedVenueId") { type = NavType.IntType; defaultValue = -1 }
            )
        ) { backStackEntry ->
            onDestinationChanged(Screen.MapPage.route)
            val latitude = backStackEntry.arguments?.getFloat("latitude") ?: 0f
            val longitude = backStackEntry.arguments?.getFloat("longitude") ?: 0f
            val selectedVenueId = backStackEntry.arguments?.getInt("selectedVenueId") ?: -1
            MapPage(
                latitude = latitude.toDouble(),
                longitude = longitude.toDouble(),
                selectedVenueId = selectedVenueId
            )
        }

        composable(Screen.Filter.route) {
            onDestinationChanged(Screen.Filter.route)
            FilterScreen(
                onApplyFilter = { filterOptions ->
                    // Handle the applied filter
                    // You might want to pass this back to your ViewModel
                    navController.popBackStack()
                },
                onDismiss = {
                    navController.popBackStack()
                }
            )
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
                onBackClick = { navController.popBackStack() },
                onOrderClick = { sectors, pricePerHour ->
                    setPricePerHour(pricePerHour)
                    setSectors(sectors)
                    showBookingBottomSheet = true
                },
                onMapClick = { latitude, longitude ->
                    navController.navigate("${Screen.MapPage.route}?latitude=${latitude}&longitude=${longitude}&selectedVenueId=${venueId}")
                }
            )
            if (showBookingBottomSheet) {
                BookingBottomSheet(
                    venueId = venueId,
                    sectors = currentSectors,
                    pricePerHour = currentPricePerHour,
                    onDismiss = { showBookingBottomSheet = false }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingBottomSheet(
    venueId: Int,
    sectors: List<String>,
    pricePerHour: String,
    onDismiss: () -> Unit
) {
    val viewModel: BookingViewModel = hiltViewModel()
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        BookingScreen(
            venueId = venueId,
            sectors = sectors,
            pricePerHour = pricePerHour,
            viewModel = viewModel,
            onOrderClick = {
                // Handle order confirmation
                onDismiss()
            },
        )
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
            ),
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}


@Composable
fun MapPage(latitude: Double? = null, longitude: Double? = null, selectedVenueId: Int = -1) {
    YandexMapScreen(
        initialLatitude = latitude,
        initialLongitude = longitude,
        initialSelectedVenueId = selectedVenueId
    )
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
            ),
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}