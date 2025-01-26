package com.bron24.bron24_android.di

import com.bron24.bron24_android.screens.auth.phone_number.PhoneNumberScreenContract
import com.bron24.bron24_android.screens.auth.phone_number.PhoneNumberScreenDirection
import com.bron24.bron24_android.screens.auth.register.RegisterScreenContract
import com.bron24.bron24_android.screens.auth.register.RegisterScreenDirection
import com.bron24.bron24_android.screens.auth.sms_otp.OTPInputContract
import com.bron24.bron24_android.screens.auth.sms_otp.OTPInputScreenDirection
import com.bron24.bron24_android.screens.booking.screens.confirmbooking.BookingConfirmationContract
import com.bron24.bron24_android.screens.booking.screens.confirmbooking.BookingConfirmationDirection
import com.bron24.bron24_android.screens.booking.screens.finishbooking.BookingSuccessContract
import com.bron24.bron24_android.screens.booking.screens.finishbooking.BookingSuccessDirection
import com.bron24.bron24_android.screens.language.LanguageSelContract
import com.bron24.bron24_android.screens.language.LanguageSelDirection
import com.bron24.bron24_android.screens.location.LocationScreenContract
import com.bron24.bron24_android.screens.location.LocationScreenDirection
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePageContract
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePageDirection
import com.bron24.bron24_android.screens.menu_pages.orders_page.OrdersPageContract
import com.bron24.bron24_android.screens.menu_pages.orders_page.OrdersPageDirection
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageDirection
import com.bron24.bron24_android.screens.on_board.OnBoardPagerContract
import com.bron24.bron24_android.screens.on_board.OnBoardPagerDirection
import com.bron24.bron24_android.screens.orderdetails.OrderDetailsContact
import com.bron24.bron24_android.screens.orderdetails.OrderDetailsDirection
import com.bron24.bron24_android.screens.search.filter_screen.FilterScreenContract
import com.bron24.bron24_android.screens.search.filter_screen.FilterScreenDirection
import com.bron24.bron24_android.screens.search.search_screen.SearchScreenContract
import com.bron24.bron24_android.screens.search.search_screen.SearchScreenDirection
import com.bron24.bron24_android.screens.settings.edit_profile.EditProfileContract
import com.bron24.bron24_android.screens.settings.edit_profile.EditProfileDirection
import com.bron24.bron24_android.screens.settings.language.LanguageContract
import com.bron24.bron24_android.screens.settings.language.LanguageDirection
import com.bron24.bron24_android.screens.splash.SplashScreenContract
import com.bron24.bron24_android.screens.splash.SplashScreenDirection
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsContract
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsDirection
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @[Binds ViewModelScoped]
    fun bindsLanguageSelDirection(impl: LanguageSelDirection): LanguageSelContract.Direction

    @[Binds ViewModelScoped]
    fun bindsOnBoardDirection(impl: OnBoardPagerDirection): OnBoardPagerContract.Direction

    @[Binds ViewModelScoped]
    fun bindsPhoneNumberDirection(impl: PhoneNumberScreenDirection): PhoneNumberScreenContract.Direction

    @[Binds ViewModelScoped]
    fun bindsOTPInputDirection(impl: OTPInputScreenDirection): OTPInputContract.Direction

    @[Binds ViewModelScoped]
    fun bindsSplashDirection(impl: SplashScreenDirection): SplashScreenContract.Direction

    @[Binds ViewModelScoped]
    fun bindsHomePageDirection(impl: HomePageDirection): HomePageContract.Direction

    @[Binds ViewModelScoped]
    fun bindsFilterDirection(impl: FilterScreenDirection): FilterScreenContract.Direction

    @[Binds ViewModelScoped]
    fun bindsRegisterDirection(impl: RegisterScreenDirection): RegisterScreenContract.Direction

    @[Binds ViewModelScoped]
    fun bindsProfilePageDirection(impl: ProfilePageDirection): ProfilePageContract.Direction

    @[Binds ViewModelScoped]
    fun bindsLanguageDirection(impl: LanguageDirection): LanguageContract.Direction

    @[Binds ViewModelScoped]
    fun bindsLocationDirection(impl: LocationScreenDirection): LocationScreenContract.Direction

    @[Binds ViewModelScoped]
    fun bindsSearchDirection(impl: SearchScreenDirection): SearchScreenContract.Direction

    @[Binds ViewModelScoped]
    fun bindsOrderPageDirection(impl: OrdersPageDirection): OrdersPageContract.Direction

    @[Binds ViewModelScoped]
    fun bindsVenueDetailsDirection(impl: VenueDetailsDirection): VenueDetailsContract.Direction

    @[Binds ViewModelScoped]
    fun bindsBookingConfirmDirection(impl: BookingConfirmationDirection): BookingConfirmationContract.Direction

    @[Binds ViewModelScoped]
    fun bindsBookingSuccessDirection(impl: BookingSuccessDirection): BookingSuccessContract.Direction

    @[Binds ViewModelScoped]
    fun bindsEditProfileDirection(impl: EditProfileDirection): EditProfileContract.Direction

    @[Binds ViewModelScoped]
    fun bindsOrderDetailsDirection(impl: OrderDetailsDirection): OrderDetailsContact.Direction

}