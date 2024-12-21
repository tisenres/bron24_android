package com.bron24.bron24_android.screens.menu_pages.orders_page

import org.orbitmvi.orbit.ContainerHost

interface OrdersPageContract {
    interface ViewModel:ContainerHost<UIState,SideEffect>{
        fun onDispatchers(intent:Intent)
    }

    data class UIState(
        val isLoading:Boolean = true,
    )

    data class SideEffect(
        val message:String
    )

    sealed interface Intent{

    }

    interface Direction{

    }
}