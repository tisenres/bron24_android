package com.bron24.bron24_android.di

import com.bron24.bron24_android.screens.auth.phone_number.PhoneNumberScreenContract
import com.bron24.bron24_android.screens.auth.phone_number.PhoneNumberScreenDirection
import com.bron24.bron24_android.screens.auth.sms_otp.OTPInputContract
import com.bron24.bron24_android.screens.auth.sms_otp.OTPInputScreenDirection
import com.bron24.bron24_android.screens.language.LanguageSelContract
import com.bron24.bron24_android.screens.language.LanguageSelDirection
import com.bron24.bron24_android.screens.on_board.OnBoardPagerContract
import com.bron24.bron24_android.screens.on_board.OnBoardPagerDirection
import com.bron24.bron24_android.screens.splash.SplashScreenContract
import com.bron24.bron24_android.screens.splash.SplashScreenDirection
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @[Binds ViewModelScoped]
    fun bindsLanguageDirection(impl: LanguageSelDirection): LanguageSelContract.Direction

    @[Binds ViewModelScoped]
    fun bindsOnBoardDirection(impl: OnBoardPagerDirection): OnBoardPagerContract.Direction

    @[Binds ViewModelScoped]
    fun bindsPhoneNumberDirection(impl: PhoneNumberScreenDirection): PhoneNumberScreenContract.Direction

    @[Binds ViewModelScoped]
    fun bindsOTPInputDirection(impl: OTPInputScreenDirection): OTPInputContract.Direction

    @[Binds ViewModelScoped]
    fun bindsSplashDirection(impl: SplashScreenDirection): SplashScreenContract.Direction

}