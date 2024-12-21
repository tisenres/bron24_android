package com.bron24.bron24_android.screens.menu_pages.home_page

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomePageVM @Inject constructor() : ViewModel(),HomePageContract.ViewModel {
    override fun onDispatchers(intent: HomePageContract.Intent): Job {
        return Job()
    }

    override fun initData(): Job {
        return Job()
    }

    override val container = container<HomePageContract.UISate, HomePageContract.SideEffect>(HomePageContract.UISate())
}