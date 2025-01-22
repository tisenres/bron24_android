package com.bron24.bron24_android.screens.settings.sms_changes

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SimCardVM @Inject constructor(): ViewModel(),SimCardContract.ViewModel {
    override fun onDispatchers(intent: SimCardContract.Intent){
        when(intent){
            SimCardContract.Intent.ClickBack->{

            }
            SimCardContract.Intent.ClickNextBtn->{

            }
        }
    }

    override val container = container<SimCardContract.UIState, SimCardContract.SideEffect>(SimCardContract.UIState())
}