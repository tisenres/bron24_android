package com.bron24.bron24_android.navigator

import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.navigator.AppScreen
import com.bron24.bron24_android.navigator.NavigationArgs
import com.bron24.bron24_android.navigator.NavigationHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigationDispatcher @Inject constructor() : AppNavigator, NavigationHandler {
    override val screenState = MutableSharedFlow<NavigationArgs>()

    private suspend fun navigation(args: NavigationArgs) {
        screenState.emit(args)
    }

    override suspend fun back() = navigation { pop() }

    override suspend fun push(screen: AppScreen) = navigation { push(screen) }

    override suspend fun popUntil(screen: AppScreen) = navigation { popUntil { it == screen } }

    override suspend fun replace(screen: AppScreen) = navigation { replace(screen) }

    override suspend fun replaceAll(screen: AppScreen) = navigation { replaceAll(screen) }

    override suspend fun backToRoot() = navigation { popUntilRoot() }
}