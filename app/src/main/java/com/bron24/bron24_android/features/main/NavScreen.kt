package com.bron24.bron24_android.features.main

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bron24.bron24_android.features.introscreens.HowItWorksPager
import com.bron24.bron24_android.features.language.presentation.LanguageSelectionScreen
import com.bron24.bron24_android.features.location.presentation.LocationRequestScreen
import kotlinx.coroutines.launch

@Composable
fun NavScreen(navController: NavHostController) {
    Surface(color = MaterialTheme.colorScheme.background) {
        val coroutineScope = rememberCoroutineScope()

        NavHost(navController = navController, startDestination = Screen.LanguageSelection.route) {
            composable(Screen.LanguageSelection.route) {
                AnimatedScreenTransition {
                    LanguageSelectionScreen(
                        viewModel = hiltViewModel(),
                        onNavigateToLocationRequest = {
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
                    HowItWorksPager(navController)
                }
            }
            composable(Screen.LocationPermission.route) {
                AnimatedScreenTransition {
                    LocationRequestScreen(
                        onAllowClick = {
                            coroutineScope.launch {
                                navController.navigate(Screen.Main.route) {
                                    popUpTo(Screen.LocationPermission.route) { inclusive = true }
                                }
                            }
                        },
                        onDenyClick = {
                            coroutineScope.launch {
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
                    AppScaffold()
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