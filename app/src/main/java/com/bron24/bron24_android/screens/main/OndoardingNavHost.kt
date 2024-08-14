package com.bron24.bron24_android.screens.main

import UserDataInputScreen
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
import com.bron24.bron24_android.domain.entity.enums.OnboardingScreen
import com.bron24.bron24_android.screens.auth.AuthViewModel
import com.bron24.bron24_android.screens.auth.OTPInputScreen
import com.bron24.bron24_android.screens.auth.PhoneNumberInputScreen
import com.bron24.bron24_android.screens.howitworks.HowItWorksPager
import com.bron24.bron24_android.screens.language.LanguageSelectionScreen
import com.bron24.bron24_android.screens.location.LocationRequestScreen
import kotlinx.coroutines.launch

@Composable
fun OnboardingNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val onboardingScreens by mainViewModel.onboardingScreensToShow.collectAsState()
    val authViewModel: AuthViewModel = hiltViewModel()

    Surface(color = MaterialTheme.colorScheme.background) {
        NavHost(
            navController = navController,
            startDestination = if (onboardingScreens.isEmpty()) Screen.Main.route else onboardingScreens.first().route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }
        ) {
            composable(Screen.LanguageSelection.route) {
//                AnimatedScreenTransition {
                    LanguageSelectionScreen(
                        viewModel = hiltViewModel(),
                        onNavigateToHowItWorksRequest = {
                            coroutineScope.launch {
                                mainViewModel.setOnboardingCompleted(OnboardingScreen.LANGUAGE)
                                navController.navigate(Screen.HowItWorksPager.route) {
                                    popUpTo(Screen.LanguageSelection.route) { inclusive = true }
                                }
                            }
                        }
                    )
//                }
            }
            composable(Screen.HowItWorksPager.route) {
//                AnimatedScreenTransition {
                    HowItWorksPager(
                        onNavigateToAuthScreens = {
                            coroutineScope.launch {
                                mainViewModel.setOnboardingCompleted(OnboardingScreen.HOWITWORKS)
                                navController.navigate(Screen.PhoneNumberInput.route) {
                                    popUpTo(Screen.HowItWorksPager.route) { inclusive = true }
                                }
                            }
                        }
                    )
//                }
            }
            composable(Screen.PhoneNumberInput.route) {
//                AnimatedScreenTransition {
                    PhoneNumberInputScreen(
                        authViewModel = authViewModel,
                        onNavigateToOTPScreen = { phoneNumber ->
                            navController.navigate("${Screen.OTPInput.route}/$phoneNumber")
                        }
                    )
//                }
            }
            composable(
                route = "${Screen.OTPInput.route}/{phoneNumber}",
                arguments = listOf(
                    navArgument("phoneNumber") { type = NavType.StringType }
                )
            ) { navBackStackEntry ->
                val phoneNumber = navBackStackEntry.arguments?.getString("phoneNumber") ?: ""
//                AnimatedScreenTransition {
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
//                }
            }
            composable(Screen.UserDataInput.route) { navBackStackEntry ->
//                AnimatedScreenTransition {
                    UserDataInputScreen(
                        authViewModel = authViewModel,
                        onSignUpVerified = {
                            navController.navigate(Screen.LocationPermission.route)
                        }
                    )
//                }
            }
            composable(Screen.LocationPermission.route) {
//                AnimatedScreenTransition {
                    // 2 situations: user can navigate to UserDataInputScreen or not
                    // So let's set ondoarding status for auth when navigation to Location screen exactly
                    mainViewModel.setOnboardingCompleted(OnboardingScreen.AUTHENTICATION)
                    LocationRequestScreen(
                        onAllowClick = {
                            coroutineScope.launch {
                                mainViewModel.setOnboardingCompleted(OnboardingScreen.LOCATION)
                                navController.navigate(Screen.Main.route) {
                                    popUpTo(Screen.LocationPermission.route) { inclusive = true }
                                }
                            }
                        },
                        onDenyClick = {
                            coroutineScope.launch {
                                mainViewModel.setOnboardingCompleted(OnboardingScreen.LOCATION)
                                navController.navigate(Screen.Main.route) {
                                    popUpTo(Screen.LocationPermission.route) { inclusive = true }
                                }
                            }
                        },
                        viewModel = hiltViewModel()
                    )
//                }
            }
            composable(Screen.Main.route) {
//                AnimatedScreenTransition {
                    MainAppScaffold()
//                }
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
        enter = fadeIn(animationSpec = tween(400)),
        exit = fadeOut(animationSpec = tween(400))
    ) {
        content()
    }
}