package com.bron24.bron24_android.screens.on_board

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
@HiltViewModel
class OnBoardPagerVM @Inject constructor(
    private val direction: OnBoardPagerContract.Direction
):ViewModel(),OnBoardPagerContract.ViewModel {
    override fun onDispatchers(intent: OnBoardPagerContract.Intent): Job = intent {
        direction.moveToNext()
    }

    override val container = container<OnBoardPagerContract.UIState, OnBoardPagerContract.SideEffect>(OnBoardPagerContract.UIState())
}