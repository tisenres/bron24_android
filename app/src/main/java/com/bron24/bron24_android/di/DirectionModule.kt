package com.bron24.bron24_android.di

import com.bron24.bron24_android.screens.language.LanguageContract
import com.bron24.bron24_android.screens.language.LanguageDirection
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @[Binds Singleton]
    fun bindsLanguageDirection(impl : LanguageDirection):LanguageContract.Direction
}