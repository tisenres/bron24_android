package com.bron24.bron24_android.screens.menu_pages.profile_page

import com.bron24.bron24_android.domain.entity.user.User
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface ProfilePageContract {
    interface ViewModel:ContainerHost<UISate, SideEffect>{
        fun onDispatchers(intent: Intent):Job
        fun initData():Job
    }

    data class UISate(
        val isLoading:Boolean = true,
        val initial:Boolean = false,
        val user:User?=null
    )

    class SideEffect(val message:String)

    sealed interface Intent{

    }
}