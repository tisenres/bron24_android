package com.bron24.bron24_android.screens.menu_pages.home_page

import androidx.lifecycle.ViewModel
import com.bron24.bron24_android.data.local.preference.LocalStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomePageVM @Inject constructor(
    private val direction: HomePageContract.Direction,
    private val localStorage: LocalStorage
) : ViewModel(),HomePageContract.ViewModel {
    init {
        localStorage.openMenu = true
    }
    override fun onDispatchers(intent: HomePageContract.Intent): Job = intent {
        when(intent){
            HomePageContract.Intent.ClickFilter -> direction.moveToFilter()
            HomePageContract.Intent.ClickSearch -> direction.moveToSearch()
        }
    }

    override fun initData(): Job {
        return Job()
    }

    override val container = container<HomePageContract.UISate, HomePageContract.SideEffect>(HomePageContract.UISate())
}