package com.bron24.bron24_android.screens.searchfilter

import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.usecases.user.GetPersonalUserDataUseCase
import com.bron24.bron24_android.domain.usecases.venue.SearchVenuesUseCase
import javax.inject.Inject

class SearchModel @Inject constructor(
    private val getPersonalUserDataUseCase: GetPersonalUserDataUseCase,
    private val searchVenuesUseCase: SearchVenuesUseCase
) {
//    fun getPersonalUserData(): User {
//        return getPersonalUserDataUseCase.execute()
//    }

    suspend fun getVenueByQuery(query: String) = searchVenuesUseCase.execute(query)
}