package com.bron24.bron24_android.screens.orderdetails

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.CustomAppBar
import com.bron24.bron24_android.components.items.CustomDialog
import com.bron24.bron24_android.screens.main.theme.Black61
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.orderdetails.layout.OrderDetailContacts
import com.bron24.bron24_android.screens.orderdetails.layout.OrderDetailHeader
import com.bron24.bron24_android.screens.orderdetails.layout.OrderDetailMap
import com.bron24.bron24_android.screens.orderdetails.layout.OrderDetailsImagePager
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsContract
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsScreenContent
import com.valentinilk.shimmer.shimmer
import org.orbitmvi.orbit.compose.collectAsState

data class OrderDetailsScreen(val id: Int) : Screen {
    @Composable
    override fun Content() {
        val viewModel: OrderDetailsContact.ViewModel = getViewModel<OrderDetailsVM>()
        val uiState = viewModel.collectAsState()
        remember {
            viewModel.initData(id)
        }
        OrderDetailsContent(state = uiState, viewModel::onDispatchers)
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun OrderDetailsContent(
    state: State<OrderDetailsContact.UIState>,
    intent: (OrderDetailsContact.Intent) -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    var openDetails by remember {
        mutableStateOf(false)
    }

    if (openDetails) {
        val venues = mutableStateOf(
            VenueDetailsContract.UIState(
                isLoading = state.value.isLoading,
                venue = state.value.venueDetails,
                imageUrls = state.value.imageUrls
            )
        )
        VenueDetailsScreenContent(state = venues, back = { openDetails = false }) {
            intent.invoke(OrderDetailsContact.Intent.ClickOrder(it))
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
        ) {
            CustomAppBar(
                title = "",
                startIcons = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "back",
                        tint = Black61
                    )
                },
                actions = {
                    if (state.value.isCanceled) {
                        TextButton(
                            onClick = {
                                showDialog = true
                            },
                            enabled = state.value.isCancelling,
                            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFF24E1E))
                        ) {
                            Text(stringResource(id = R.string.cancel_order), color = Color.Red)
                        }
                    }
                }
            ) {
                intent.invoke(OrderDetailsContact.Intent.Back)
            }

            OrderDetailsItem(
                state = state,
                modifier = Modifier,
                navigateToVenueDetails = {
                    openDetails = true
                    intent.invoke(OrderDetailsContact.Intent.OpenVenueDetails(state.value.order?.venueId ?: 0))
                }
            )

        }
    }

    if (showDialog) {
        CustomDialog(
            message = stringResource(id = R.string.cancel_dialog),
            yes = stringResource(id = R.string.yes),
            no = stringResource(id = R.string.no),
            onDismiss = { showDialog = false }
        ) {
            intent.invoke(OrderDetailsContact.Intent.ClickCancel(state.value.order?.id ?: 0))
            showDialog = false
        }
    }
}

@Composable
private fun OrderDetailsItem(
    state: State<OrderDetailsContact.UIState>,
    modifier: Modifier = Modifier,
    navigateToVenueDetails: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            if (state.value.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmer()
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
            } else {
                OrderDetailsImagePager(
                    imageUrls = state.value.imageUrls,
                    modifier = Modifier
                )
            }

        }
        item {
            if (state.value.isLoading) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .fillMaxWidth(0.8f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmer()
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .fillMaxWidth(0.5f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmer()
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .fillMaxWidth(0.5f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmer()
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth(0.5f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmer()
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )
                }
            } else {
                OrderDetailHeader(
                    order = state.value.order,
                    modifier = Modifier
                )
            }

        }
        item {
            OrderDetailMap(
                state = state,
                modifier = Modifier
            )
        }

        item {
            if (state.value.isLoading) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(0.6f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmer()
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmer()
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
            } else {
                OrderDetailContacts(
                    order = state.value.order,
                    modifier = Modifier
                )
            }
        }

        item {
            if (state.value.isLoading) {
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmer()
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
            } else {
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .align(Alignment.Center),
                        onClick = navigateToVenueDetails,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF6D6D6D))
                    ) {
                        Text(
                            text = stringResource(id = R.string.view_deatil),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = gilroyFontFamily
                        )
                    }
                }
            }
        }
    }
}

