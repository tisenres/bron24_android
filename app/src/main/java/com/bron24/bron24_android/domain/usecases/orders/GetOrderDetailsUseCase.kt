package com.bron24.bron24_android.domain.usecases.orders

import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.domain.entity.order.OrderDetails
import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.repository.OrdersRepository
import com.bron24.bron24_android.domain.repository.VenueRepository
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetOrderDetailsUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository,
    private val venueRepository: VenueRepository,
    private val currentLocationUseCase: GetCurrentLocationUseCase,
    private val getLocationPermissionUseCase: CheckLocationPermissionUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun execute(orderId: Int): Flow<Pair<OrderDetails,List<String>>> = flow {
        getLocationPermissionUseCase.invoke().collect{
            when(it){
                LocationPermissionState.GRANTED -> {
                    currentLocationUseCase.execute().collect{
                        val orderData = ordersRepository.getOrderDetails(orderId, latitude = it.latitude, longitude = it.longitude)
                        emitAll(orderData.flatMapConcat { data ->
                            flow {
                                val images = venueRepository.getVenuePictures(data.data.venueId)
                                emit(Pair(first = data.data.toDomainModel(), second = images))
                            }
                        })
                    }
                }
                LocationPermissionState.DENIED -> {
                    val orderData = ordersRepository.getOrderDetails(orderId)
                    emitAll(orderData.flatMapConcat { data ->
                        flow {
                            val images = venueRepository.getVenuePictures(data.data.venueId)
                            emit(Pair(first = data.data.toDomainModel(), second = images))
                        }
                    })
                }
            }
        }

    }

    suspend fun cancelOrder(orderId: Int): Flow<Boolean> {
        return ordersRepository.cancelOrder(orderId).map {
            it.success
        }
    }
}
