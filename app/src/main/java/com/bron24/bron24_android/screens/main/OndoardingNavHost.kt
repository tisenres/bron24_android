package com.bron24.bron24_android.screens.main

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.screens.auth.AuthViewModel
import com.bron24.bron24_android.screens.auth.OTPInputScreen
import com.bron24.bron24_android.screens.auth.PhoneNumberInputScreen
import com.bron24.bron24_android.screens.auth.UserDataInputScreen
import com.bron24.bron24_android.screens.howitworks.HowItWorksPager
import com.bron24.bron24_android.screens.language.LanguageSelectionScreen
import com.bron24.bron24_android.screens.location.LocationRequestScreen
import kotlinx.coroutines.launch

@Composable
fun OndoardingNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val isOnboardingCompleted by mainViewModel.isOnboardingCompleted.collectAsState()
    val authViewModel: AuthViewModel = hiltViewModel()

    Surface(color = MaterialTheme.colorScheme.background) {
        NavHost(
            navController = navController,
            startDestination = if (isOnboardingCompleted) Screen.Main.route else Screen.LanguageSelection.route
        ) {
            composable(Screen.LanguageSelection.route) {
                AnimatedScreenTransition {
                    LanguageSelectionScreen(
                        viewModel = hiltViewModel(),
                        onNavigateToHowItWorksRequest = {
                            coroutineScope.launch {
                                navController.navigate(Screen.HowItWorksPager.route) {
                                    popUpTo(Screen.LanguageSelection.route) { inclusive = true }
                                }
                            }
                        }
                    )
                }
            }
            composable(Screen.HowItWorksPager.route) {
                AnimatedScreenTransition {
                    HowItWorksPager(
                        onNavigateToAuthScreens = {
                            coroutineScope.launch {
                                navController.navigate(Screen.PhoneNumberInput.route) {
                                    popUpTo(Screen.HowItWorksPager.route) { inclusive = true }
                                }
                            }
                        }
                    )
                }
            }
            composable(Screen.PhoneNumberInput.route) { navBackStackEntry ->
                AnimatedScreenTransition {
                    PhoneNumberInputScreen(
                        authViewModel = authViewModel,
                        onNavigateToOTPScreen = { phoneNumber ->
                            navController.navigate("${Screen.OTPInput.route}/$phoneNumber")
                        }
                    )
                }
            }
            composable(
                route = "${Screen.OTPInput.route}/{phoneNumber}",
                arguments = listOf(
                    navArgument("phoneNumber") { type = NavType.StringType }
                )
            ) { navBackStackEntry ->
                val phoneNumber = navBackStackEntry.arguments?.getString("phoneNumber") ?: ""
                AnimatedScreenTransition {
                    OTPInputScreen(
                        authViewModel = authViewModel,
                        onOTPVerified = {
                            navController.navigate(Screen.UserDataInput.route) {
                                popUpTo(Screen.OTPInput.route) { inclusive = true }
                            }
                        },
                        onBackClick = {
                            authViewModel.clearState()
                            navController.popBackStack()
                        },
                        phoneNumber = phoneNumber
                    )
                }
            }
            composable(Screen.UserDataInput.route) { navBackStackEntry ->
                AnimatedScreenTransition {
                    UserDataInputScreen(
                        authViewModel = authViewModel,
                        onSignUpVerified = {
                            navController.navigate(Screen.LocationPermission.route)
                        }
                    )
                }
            }
            composable(Screen.LocationPermission.route) {
                AnimatedScreenTransition {
                    LocationRequestScreen(
                        onAllowClick = {
                            coroutineScope.launch {
                                mainViewModel.setOnboardingCompleted()
                                navController.navigate(Screen.Main.route) {
                                    popUpTo(Screen.LocationPermission.route) { inclusive = true }
                                }
                            }
                        },
                        onDenyClick = {
                            coroutineScope.launch {
                                mainViewModel.setOnboardingCompleted()
                                navController.navigate(Screen.Main.route) {
                                    popUpTo(Screen.LocationPermission.route) { inclusive = true }
                                }
                            }
                        },
                        viewModel = hiltViewModel()
                    )
                }
            }
            composable(Screen.Main.route) {
                AnimatedScreenTransition {
                    MainAppScaffold()
                }
            }
        }
    }
}

@Composable
fun AnimatedScreenTransition(content: @Composable () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(700)),
        exit = fadeOut(animationSpec = tween(700))
    ) {
        content()
    }
}