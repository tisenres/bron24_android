package com.bron24.bron24_android.screens.menu_pages.home_page

import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.entity.venue.Venue
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface HomePageContract {
    interface ViewModel:ContainerHost<UISate, SideEffect>{
        fun onDispatchers(intent: Intent): Job
        fun initData(): Job
    }

    data class UISate(
        val isLoading:Boolean = true,
        val initial:Boolean = false,
        val user: User?=null,
        val itemData:List<Venue> = emptyList(),
        val selectedSort:String? = null,
        val userLocation:Location = Location(33.33,33.33)
    )

    class SideEffect(val message:String)

    sealed interface Intent{
        data object ClickSearch:Intent
        data object ClickFilter:Intent
        data class SelectedSort(val name:String):Intent
        data object Refresh:Intent
    }

    interface Direction{
        suspend fun moveToSearch()
        suspend fun moveToFilter()
    }
}