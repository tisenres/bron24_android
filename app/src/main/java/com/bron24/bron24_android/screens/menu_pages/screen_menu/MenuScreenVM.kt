package com.bron24.bron24_android.screens.menu_pages.screen_menu

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MenuScreenVM @Inject constructor(
    private val direction: MenuScreenContract.Direction
): ViewModel(),MenuScreenContract.ViewModel {
    override fun onDispatchers(intent: MenuScreenContract.Intent): Job = intent {
        direction.moveToPhone()
    }

    override val container = container<MenuScreenContract.UIState, MenuScreenContract.SideEffect>(MenuScreenContract.UIState())
}