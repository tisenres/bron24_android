package com.bron24.bron24_android.screens.search.filter_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
@HiltViewModel
class FilterScreenVM @Inject constructor(
    private val direction: FilterScreenContract.Direction,
):ViewModel(),FilterScreenContract.ViewModel{
    override fun onDispatchers(intent: FilterScreenContract.Intent): Job = intent {
        when(intent){
            FilterScreenContract.Intent.ClickBack -> direction.back()
            is FilterScreenContract.Intent.ClickFilterBtn ->{
                direction.back()
            }
        }
    }

    override val container = container<FilterScreenContract.UIState, FilterScreenContract.SideEffect>(FilterScreenContract.UIState())
}