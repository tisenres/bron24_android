package com.bron24.bron24_android.di
import com.bron24.bron24_android.navigator.AppNavigationDispatcher
import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.navigator.NavigationHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    @Binds
    fun bindAppNavigator(dispatcher: AppNavigationDispatcher): AppNavigator
    @Binds
    fun bindNavigationHandler(dispatcher: AppNavigationDispatcher): NavigationHandler
}