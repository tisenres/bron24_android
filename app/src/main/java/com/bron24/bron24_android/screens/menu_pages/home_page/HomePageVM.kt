package com.bron24.bron24_android.screens.menu_pages.home_page

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenuesUseCase
import com.bron24.bron24_android.screens.auth.register.RegisterScreenContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomePageVM @Inject constructor(
    private val direction: HomePageContract.Direction,
    private val localStorage: LocalStorage,
    private val getVenuesUseCase: GetVenuesUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
) : ViewModel(), HomePageContract.ViewModel {
    init {
        localStorage.openMenu = true
    }

    override fun onDispatchers(intent: HomePageContract.Intent): Job = intent {
        when (intent) {
            HomePageContract.Intent.ClickFilter -> direction.moveToFilter()
            HomePageContract.Intent.ClickSearch -> direction.moveToSearch()
            is HomePageContract.Intent.SelectedSort -> {
                reduce { state.copy(selectedSort = intent.name) }
            }
            HomePageContract.Intent.Refresh -> {
                getVenuesUseCase.invoke().onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    it.onSuccess {
                        reduce { state.copy(isLoading = false, itemData = it) }
                    }.onFailure {
                        postSideEffect(it.message.toString())
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    override fun initData(): Job = intent {
        getVenuesUseCase.invoke().onEach {
            it.onSuccess {
                reduce { state.copy(isLoading = false, itemData = it) }
            }.onFailure {

            }
        }.launchIn(viewModelScope)
        getCurrentLocationUseCase.execute().onEach {
            Log.d("AAA", "initData:$it ")
            reduce { state.copy(userLocation = it) }
        }.launchIn(viewModelScope)
    }

    private fun postSideEffect(message: String) {
        intent {
            postSideEffect(HomePageContract.SideEffect(message))
        }
    }

    override val container =
        container<HomePageContract.UISate, HomePageContract.SideEffect>(HomePageContract.UISate())
}