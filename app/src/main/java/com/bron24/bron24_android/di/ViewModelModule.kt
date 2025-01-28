package com.bron24.bron24_android.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePageVM
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
interface ViewModelModule {
    @Binds
    @IntoMap
    @ScreenModelKey(HomePageVM::class)
    fun homePageViewModel(model: HomePageVM): ScreenModel
}