package com.bron24.bron24_android.screens.menu_pages.profile_page

import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.settings.edit_profile.EditProfileScreen
import com.bron24.bron24_android.screens.settings.favorite.FavoriteScreen
import com.bron24.bron24_android.screens.settings.language.LanguageScreen
import javax.inject.Inject

class ProfilePageDirection @Inject constructor(private val appNavigator: AppNavigator) : ProfilePageContract.Direction {
    override suspend fun openEdit() {
        appNavigator.push(EditProfileScreen())
    }

    override suspend fun openChangeLanguage() {
        appNavigator.push(LanguageScreen())
    }

    override suspend fun openFavorites() {
        appNavigator.push(FavoriteScreen())
    }

    override suspend fun openAboutUs() {

    }

    override suspend fun openAddVenue() {

    }
}