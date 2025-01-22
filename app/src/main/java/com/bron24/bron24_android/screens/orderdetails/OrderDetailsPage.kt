package com.bron24.bron24_android.screens.orderdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.entity.order.OrderStatus
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.orderdetails.layout.OrderDetailContacts
import com.bron24.bron24_android.screens.orderdetails.layout.OrderDetailHeader
import com.bron24.bron24_android.screens.orderdetails.layout.OrderDetailMap
import com.bron24.bron24_android.screens.orderdetails.layout.OrderDetailsImagePager
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsPage(
    orderId: Long,
    viewModel: OrderDetailsViewModel = hiltViewModel(),
    navController: NavHostController
) {

    var isFirstOpen by remember { mutableStateOf(true) }
    val orderState by viewModel.orderState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Timber.tag("TAGTAG").d("LaunchedEffect")
        if (isFirstOpen) {
            viewModel.fetchOrder(orderId)
            isFirstOpen = false
        }
    }
    LaunchedEffect(orderState) {
        if (orderState is OrderDetailState.Success) {
            if ((orderState as OrderDetailState.Success).isCanceled) {
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "back"
                        )
                    }
                },
                actions = {
                    val state = orderState
                    if (state is OrderDetailState.Success && state.data.status != OrderStatus.CANCELLED.name) {
                        TextButton(
                            onClick = {
                                viewModel.cancelOrder(orderId)
                            },
                            enabled = !state.isCancelling,
                            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFF24E1E))
                        ) {
                            if (state.isCancelling) {
                                CircularProgressIndicator(modifier = Modifier.size(16.dp))
                            } else {
                                Text("Cancel order")
                            }
                        }
                    }
                })
        }
    ) { paddingValues ->
        when (orderState) {
            OrderDetailState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is OrderDetailState.Success -> {
                val order = (orderState as OrderDetailState.Success).data
                OrderDetailsContent(
                    order = order,
                    modifier = Modifier.padding(paddingValues),
                    navigateToVenueDetails = {
//                        navController.navigate(
//                            Screen.VenueDetails.route.replace("{venueId}", order.venueId.toString())
//                        )
                    })
            }

            is OrderDetailState.Error -> {
                val errorMessage = (orderState as OrderDetailState.Error).message
                ToastManager.showToast(errorMessage, ToastType.ERROR)

            }
        }

    }
}

@Composable
private fun OrderDetailsContent(
    order: Order,
    modifier: Modifier = Modifier,
    navigateToVenueDetails: () -> Unit
) {
    LazyColumn(modifier = modifier) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            OrderDetailsImagePager(
                imageUrls = order.previewImage?:"",
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            OrderDetailHeader(
                order = order,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            OrderDetailMap(
                order = order,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            OrderDetailContacts(
                order = order,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 24.dp, end = 24.dp),
                    onClick = navigateToVenueDetails,
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF6D6D6D))
                ) {
                    Text(
                        "View venue details",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = gilroyFontFamily
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

