package com.bron24.bron24_android.screens.booking.screens.confirmbooking

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.helper.util.PhoneNumberVisualTransformation
import com.bron24.bron24_android.helper.util.formatPhoneNumber
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.text.SimpleDateFormat
import java.util.Locale

data class BookingConfirmationScreen(val bookingInfo:VenueOrderInfo):Screen{
    @Composable
    override fun Content() {
        val viewModel:BookingConfirmationContract.ViewModel = getViewModel<BookingConfirmationVM>()
        val uiState = viewModel.collectAsState()
        remember {
            viewModel.initData(bookingInfo)
        }
        viewModel.collectSideEffect {
            ToastManager.showToast(it.message,ToastType.INFO)
        }
        BookingConfirmationContent(uiState = uiState,viewModel::onDispatchers)
    }
}

@Composable
fun BookingConfirmationContent(
    uiState:State<BookingConfirmationContract.UIState>,
    intent:(BookingConfirmationContract.Intent)->Unit
) {
    var showPaymentMethods by remember { mutableStateOf(false) }
    var showPromoCode by remember { mutableStateOf(false) }

    // State variables for selected payment method and promo code
    val cash = stringResource(id = R.string.cash)
    var selectedPaymentMethod by remember { mutableStateOf(cash) }
    var appliedPromoCode by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberLazyListState()

    val toolbarVisible = true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AnimatedToolbar(
            visible = toolbarVisible,
            title = stringResource(id = R.string.booking_info),
            onBackClick = {intent.invoke(BookingConfirmationContract.Intent.Back)},
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
        )

        if (uiState.value.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 24.dp, end = 24.dp),
                state = scrollState
            ) {
                item {
                    uiState.value.booking?.let {
                        BookingInfoCard( it, uiState.value.secondPhoneNumber?:""){
                            intent.invoke(BookingConfirmationContract.Intent.UpdatePhone(it))
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        PaymentMethodButton(
                            selectedPaymentMethod = selectedPaymentMethod,
                            onClick = {
                                showPaymentMethods = true
                            }
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        PromoCodeButton(
                            appliedPromoCode = appliedPromoCode,
                            onClick = {
                                showPromoCode = true
                            }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        TotalAmount(it.cost)
                        Spacer(modifier = Modifier.height(20.dp))
                    } ?: run {
                        // Handle case where booking is null (e.g., error or no data)
                        Text("Failed to load booking information", color = Color.Red)
                    }
                }
            }

            // Place the ConfirmButton at the bottom
            ConfirmButton(
                isEnabled = true,
                onClick = {
                    intent.invoke(BookingConfirmationContract.Intent.Confirm)
                },
                title = stringResource(id = R.string.confirm),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            )
        }
    }

    if (showPaymentMethods) {
        PaymentMethodsBottomSheet(
            onDismiss = {
                showPaymentMethods = false
            },
            onPaymentMethodSelected = { method ->
                selectedPaymentMethod = method
                showPaymentMethods = false
            }
        )
    }

    if (showPromoCode) {
        PromoCodeBottomSheet(
            onDismiss = {
                showPromoCode = false
            },
            onApply = { code ->
                appliedPromoCode = code
                showPromoCode = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedToolbar(
    visible: Boolean,
    title: String?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.height(46.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title ?: "Unknown field",
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF000000),
                            lineHeight = 22.sp,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color(0xFF000000),
                navigationIconContentColor = Color(0xFF000000)
            )
        )
    }
}

@Composable
fun BookingInfoCard(
    booking: Booking,
    secondPhoneNumber: String,
    updatePhone: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(15.dp))
    ) {
        Column(modifier = Modifier.padding(vertical = 15.dp)) {
            VenueInfo(
                booking.venueName ?: "",
                booking.venueAddress ?: "",
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 15.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                BookingDetail(stringResource(id = R.string.date_x), formatDate(booking.bookingDate))
                Spacer(modifier = Modifier.height(15.dp))

                BookingDetail(stringResource(id = R.string.total_hour), (booking.totalHours.toString() + " ${stringResource(id = R.string.hours)}"))
                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.time_x),
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = Color(0xFF949494),
                            lineHeight = 20.sp
                        )
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        booking.timeSlots.forEach { timeSlot ->
                            Text(
                                text = "${timeSlot.startTime} - ${timeSlot.endTime}",
                                style = TextStyle(
                                    fontFamily = gilroyFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    color = Color.Black,
                                    lineHeight = 20.sp
                                ),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))
                BookingDetail(
                    stringResource(id = R.string.stadium_part).uppercase(Locale.getDefault()),
                    when (booking.sector) {
                        "X" -> stringResource(id = R.string.full_stadion)
                        else -> "${stringResource(id = R.string.sector)} ${booking.sector}"
                    }
                )
            }
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 15.dp)
            )

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                BookingDetail(stringResource(id = R.string.full_name), booking.firstName + " " + booking.lastName)
                Spacer(modifier = Modifier.height(15.dp))
                BookingDetail(stringResource(id = R.string.first_phone), booking.phoneNumber.formatPhoneNumber())
                Spacer(modifier = Modifier.height(15.dp))
                SecondNumberField(
                    value = secondPhoneNumber,
                    onValueChange = { newValue ->
                        if (newValue.startsWith("+998")) {
                            updatePhone(newValue)
                        }
                    },
                    updatePhone = updatePhone
                )
            }
        }
    }
}

@Composable
fun SecondNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    updatePhone:(String)->Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = value.removePrefix("+998"))) }

    LaunchedEffect(value) {
        textFieldValue = textFieldValue.copy(
            text = value.removePrefix("+998"),
            selection = TextRange(value.length)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(id = R.string.second_phone),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color(0xFF949494),
                lineHeight = 20.sp,
            ),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(40.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFFD1D1D1),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "+998",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        lineHeight = 20.sp
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = { newValue ->
                        val digitsOnly = newValue.text.filter { it.isDigit() }
                        if (digitsOnly.length <= 9) {
                            val fullNumber = "+998$digitsOnly"
                            textFieldValue = newValue.copy(
                                text = digitsOnly,
                                selection = TextRange(digitsOnly.length)
                            )
                            onValueChange(fullNumber)
                            updatePhone(fullNumber)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    visualTransformation = PhoneNumberVisualTransformation(),
                    singleLine = true,
                    textStyle = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        lineHeight = 20.sp
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun VenueInfo(venue: String, address: String, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.venue_icon),
            contentDescription = "Venue",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column {
            Text(
                text = venue,
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    lineHeight = 22.sp,
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = address,
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color(0xFF949494),
                    lineHeight = 16.sp,
                )
            )
        }
    }
}

@Composable
fun BookingDetail(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color(0xFF949494),
                lineHeight = 20.sp,
            )
        )
        Text(
            text = value,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp,
            ),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun PaymentMethodButton(selectedPaymentMethod: String?, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        contentPadding = PaddingValues(start = 15.dp, end = 20.dp, top = 21.dp, bottom = 21.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_payment_24),
                        contentDescription = "Payment",
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.width(17.dp))
                Text(
                    selectedPaymentMethod ?: "Choose Payment",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        lineHeight = 20.sp,
                    )
                )
            }
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Choose",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun PromoCodeButton(appliedPromoCode: String?, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        contentPadding = PaddingValues(start = 15.dp, end = 20.dp, top = 21.dp, bottom = 21.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.promo_code),
                        contentDescription = "Promo Code",
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.width(17.dp))
                Text(
                    appliedPromoCode ?: stringResource(id = R.string.promo_code),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        lineHeight = 20.sp,
                    )
                )
            }
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Choose",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun TotalAmount(total: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.total).toUpperCase(Locale.ROOT),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFF949494),
                lineHeight = 20.sp,
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Text(
            "$total ${stringResource(id = R.string.som)}",
            fontSize = 18.sp,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                lineHeight = 20.sp
            )
        )
    }
}

@Composable
fun ConfirmButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = ButtonDefaults.buttonColors(
        containerColor = if (isEnabled) Color(0xFF32B768) else Color(0xFFE4E4E4),
        contentColor = Color.White,
        disabledContainerColor = Color(0xFFE4E4E4),
        disabledContentColor = Color.Gray
    )

    Button(
        onClick = onClick,
        enabled = isEnabled,
        shape = RoundedCornerShape(10.dp),
        colors = colors,
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = gilroyFontFamily,
            color = if (isEnabled) Color.White else Color.Gray,
            lineHeight = 32.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodsBottomSheet(
    onDismiss: () -> Unit,
    onPaymentMethodSelected: (String) -> Unit
) {
    val paymentMethods = listOf(stringResource(id = R.string.cash))
    val cash = stringResource(id = R.string.cash)
    var selectedMethod by remember { mutableStateOf(cash) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp)) {
            Text(
                text = stringResource(id = R.string.payment),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    lineHeight = 20.sp,
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(20.dp))
            paymentMethods.forEach { method ->
                PaymentOption(
                    title = method,
                    iconRes = if (method == cash) R.drawable.cash_pic else R.drawable.uzcard,
                    isSelected = selectedMethod == method,
                    onClick = {
                        selectedMethod = method
                        onPaymentMethodSelected(method)
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            AddCardOption()
        }
    }
}

@Composable
fun PaymentOption(title: String, iconRes: Int, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(15.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 16.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(17.dp))
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Black,
                    lineHeight = 20.sp,
                ),
            )
            Spacer(modifier = Modifier.weight(1f))
            RadioButton(
                selected = isSelected,
                onClick = {},
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFF32B768),
                    unselectedColor = Color.Gray
                )
            )
        }
    }
}

@Composable
fun AddCardOption() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .background(Color(0xFFE3E3E3))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_park_outline_add),
                contentDescription = "Add",
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.pay_by_card), style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Black,
                    lineHeight = 20.sp,
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Add",
                tint = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoCodeBottomSheet(
    onDismiss: () -> Unit,
    onApply: (String) -> Unit
) {
    var promoCode by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
            Box {
                BasicTextField(
                    value = promoCode,
                    onValueChange = { promoCode = it.uppercase() },  // Automatically uppercase the input
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFC0C0C0), RoundedCornerShape(10.dp))
                        .padding(16.dp),
                    textStyle = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    singleLine = true
                )

                if (promoCode.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.promo_code),
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color(0xFFC0C0C0)
                        ),
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp)  // Padding to align with BasicTextField text
                    )
                }
            }
            Spacer(modifier = Modifier.height(13.dp))
            ConfirmButton(
                isEnabled = promoCode.isNotBlank(),
                onClick = {
                    onApply(promoCode)
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                },
                title = stringResource(id = R.string.apply)
            )
        }
    }
}

fun formatDate(inputDate: String): String {
    // Define the input and output date formats
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    // Parse the input date and format it to the desired output
    val parsedDate = inputFormatter.parse(inputDate)
    return outputFormatter.format(parsedDate)
}

@Preview
@Composable
fun ConfirmButtonPreview() {
    AddCardOption()
}
