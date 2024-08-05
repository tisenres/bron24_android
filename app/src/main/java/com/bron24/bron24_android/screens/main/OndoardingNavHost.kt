package com.bron24.bron24_android.screens.main

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bron24.bron24_android.screens.auth.OTPInputScreen
import com.bron24.bron24_android.screens.auth.PhoneNumberInputScreen
import com.bron24.bron24_android.screens.howitworks.HowItWorksPager
import com.bron24.bron24_android.screens.language.LanguageSelectionScreen
import com.bron24.bron24_android.screens.location.LocationRequestScreen
import kotlinx.coroutines.launch

@Composable
fun OndoardingNavHost(navController: NavHostController, mainViewModel: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val isOnboardingCompleted by mainViewModel.isOnboardingCompleted.collectAsState()

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
            composable(Screen.PhoneNumberInput.route) {
                AnimatedScreenTransition {
                    PhoneNumberInputScreen(
                        authViewModel = hiltViewModel(),
                        onContinueClick = {
                            navController.navigate(Screen.OTPInput.route)
                        }
                    )
                }
            }
            composable(Screen.OTPInput.route) {
                AnimatedScreenTransition {
                    OTPInputScreen(
                        authViewModel = hiltViewModel(),
                        onOTPVerified = {
                            navController.navigate(Screen.LocationPermission.route) {
                                popUpTo(Screen.OTPInput.route) { inclusive = true }
                            }
                        },
                        onBackClick = {

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