package com.bron24.bron24_android.screens.menu_pages.screen_menu

import android.util.Printer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.usecases.update_profile.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MenuScreenVM @Inject constructor(
    private val direction: MenuScreenContract.Direction,
    private val logoutUseCase: LogoutUseCase
): ViewModel(),MenuScreenContract.ViewModel {
    override fun onDispatchers(intent: MenuScreenContract.Intent): Job = intent {
        logoutUseCase.invoke().launchIn(viewModelScope)
        direction.moveToPhone()
    }

    override val container = container<MenuScreenContract.UIState, MenuScreenContract.SideEffect>(MenuScreenContract.UIState())
}