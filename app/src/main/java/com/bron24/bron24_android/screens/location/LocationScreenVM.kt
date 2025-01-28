package com.bron24.bron24_android.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.screens.auth.register.RegisterScreenContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LocationScreenVM @Inject constructor(
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase,
    private val direction: LocationScreenContract.Direction,
): ViewModel(),LocationScreenContract.ViewModel {

    override fun onDispatchers(intent: LocationScreenContract.Intent) = intent {
        when(intent){
            LocationScreenContract.Intent.ClickAllow -> {
                direction.moveToNext()
            }
            LocationScreenContract.Intent.ClickDeny -> {
                direction.moveToNext()
            }
        }
    }
    private fun postSideEffect(message: String) {
        intent {
            postSideEffect(LocationScreenContract.SideEffect(message))
        }
    }

    override val container = container<LocationScreenContract.UIState, LocationScreenContract.SideEffect>(LocationScreenContract.UIState())
}
