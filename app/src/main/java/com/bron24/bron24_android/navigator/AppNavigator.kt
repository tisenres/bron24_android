package com.bron24.bron24_android.navigator

import cafe.adriel.voyager.core.screen.Screen
typealias AppScreen = Screen

interface AppNavigator {
    suspend fun back()
    suspend fun push(screen: AppScreen)
    suspend fun popUntil(screen: AppScreen)
    suspend fun replace(screen: AppScreen)
    suspend fun replaceAll(screen: AppScreen)
    suspend fun backToRoot()
}