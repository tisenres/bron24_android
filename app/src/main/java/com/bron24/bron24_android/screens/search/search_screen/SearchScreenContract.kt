package com.bron24.bron24_android.screens.search.search_screen

import com.bron24.bron24_android.domain.entity.venue.Venue
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface SearchScreenContract {
    interface ViewModel:ContainerHost<UIState, SideEffect>{
        fun onDispatchers(intent: Intent):Job
    }

    class UIState(
        val isLoading:Boolean = true,
        val history:List<String> = emptyList(),
        val searchResult: List<Venue> = emptyList(),
    )

    class SideEffect(val message:String)

    sealed interface Intent{
        data object Back:Intent
    }

    interface Direction{
        suspend fun back()
        suspend fun moveToInfo(venue: Venue)
    }
}