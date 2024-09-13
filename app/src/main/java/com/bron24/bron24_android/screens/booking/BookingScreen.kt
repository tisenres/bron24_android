package com.bron24.bron24_android.screens.booking

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.booking.DateItem
import com.bron24.bron24_android.domain.entity.booking.Sector
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun BookingScreen(
    viewModel: BookingViewModel = hiltViewModel(),
    venueId: Int,
    pricePerHour: String,
    onOrderClick: () -> Unit
) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val availableDates by viewModel.availableDates.collectAsState()
    val visibleMonthYear by viewModel.visibleMonthYear.collectAsState()
    val selectedStadiumPart by viewModel.selectedStadiumPart.collectAsState()
    val availableStadiumParts by viewModel.availableStadiumParts.collectAsState()
    val selectedTime by viewModel.selectedTime.collectAsState()
    val availableTimes by viewModel.availableTimes.collectAsState()
    val bookingState by viewModel.bookingState.collectAsState()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val density = LocalDensity.current

    LaunchedEffect(Unit) {
        viewModel.selectDate(Calendar.getInstance().timeInMillis) // Select today's date by default
    }

    LaunchedEffect(Unit) {
        viewModel.selectDate(Calendar.getInstance().timeInMillis) // Select today's date by default
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            StadiumPartSection(
                sectors = availableStadiumParts.map { Sector(it, it == selectedStadiumPart) },
                onSectorSelected = {
                    viewModel.selectStadiumPart(it)
                    viewModel.fetchTimeSlots()
                }
            )

            DateSection(
                dates = availableDates,
                visibleMonthYear = visibleMonthYear,
                onDateSelected = { timestamp ->
                    viewModel.selectDate(timestamp)
                    viewModel.fetchTimeSlots()


                    coroutineScope.launch {
                        // Scroll to center the selected date

                        val selectedIndex = availableDates.indexOfFirst { it.isSelected }


                        if (selectedIndex != -1) {
                            val itemWidth = 72.dp // Width of date item + spacing
                            val centerOffset = scrollState.maxValue / 2
                            val targetScroll = with(density) {
                                (selectedIndex * itemWidth.toPx()).toInt() - centerOffset
                            }
                            scrollState.animateScrollTo(
                                targetScroll.coerceIn(0, scrollState.maxValue)
                            )
                        }
                    }
                },
                onMonthClick = { viewModel.showDatePicker() },
                onVisibleDatesChanged = viewModel::updateVisibleMonthYear,
                scrollState = scrollState
            )

            AvailableTimesSection(
                timeSlots = availableTimes.map { TimeSlot(it, it == selectedTime, true) },
                onTimeSelected = { viewModel.selectTime(it) }
            )
        }

        PricingSection(
            pricePerHour = pricePerHour,
            onOrderClick = {
                viewModel.createBooking(venueId = 1, userId = "user123")
                onOrderClick()
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    // Handle booking state
    when (bookingState) {
        is BookingState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }

        is BookingState.Error -> {
            Text("Error: ${(bookingState as BookingState.Error).message}")
        }

        is BookingState.Success -> {
            LaunchedEffect(bookingState) {
                // Handle successful booking, e.g., navigate to confirmation screen
            }
        }

        else -> {} // Idle state, do nothing
    }

    // DatePicker Dialog
    val showDatePicker by viewModel.showDatePicker.collectAsState()
    if (showDatePicker) {
        val currentDate = Calendar.getInstance()
        val maxDate = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
        }

        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                currentDate.set(year, month, dayOfMonth)
                coroutineScope.launch {
                    viewModel.selectDate(currentDate.timeInMillis)
                    viewModel.fetchTimeSlots()
                }
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = maxDate.timeInMillis
            setOnDismissListener { viewModel.onDatePickerShown() }
            show()
        }
    }
}

@Composable
fun StadiumPartSection(
    sectors: List<Sector>,
    onSectorSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            "Stadium Part",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = Color(0xFF3C2E56),
                lineHeight = 28.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(19.dp)
        ) {
            sectors.forEach { sector ->
                SectorButton(
                    sector = sector,
                    onClick = { onSectorSelected(sector.name) }
                )
            }
        }
    }
}

@Composable
fun SectorButton(
    sector: Sector,
    onClick: () -> Unit
) {
    val backgroundColor = if (sector.isSelected) Color(0xFF32B768) else Color(0xFFF5F5F5)
    val textColor = if (sector.isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(vertical = 13.dp, horizontal = 29.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Sector ${sector.name}",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = textColor,
                lineHeight = 20.sp,
            ),
        )
    }
}

@Composable
fun DateSection(
    dates: List<DateItem>,
    visibleMonthYear: String,
    onDateSelected: (Long) -> Unit,
    onMonthClick: () -> Unit,
    onVisibleDatesChanged: (DateItem) -> Unit,
    scrollState: ScrollState
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onMonthClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Date",
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    color = Color(0xFF3C2E56),
                    lineHeight = 28.sp,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.baseline_calendar_today_24),
                contentDescription = "Calendar",
                modifier = Modifier.size(16.dp),
                colorFilter = ColorFilter.tint(Color(0xFF949494)),
            )
            Text(
                visibleMonthYear,
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF949494),
                    lineHeight = 20.sp,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 7.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
        ) {
            dates.forEach { dateItem ->
                DateItem(
                    dateItem = dateItem,
                    onClick = { onDateSelected(dateItem.timestamp) },
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        val isVisible = coordinates.boundsInRoot().let { bounds ->
                            bounds.left >= 0 && bounds.right <= coordinates.size.width
                        }
                        if (isVisible) {
                            onVisibleDatesChanged(dateItem)
                        }
                    }
                )
            }
        }
    }

    // Center the initially selected date
    LaunchedEffect(dates) {
        val selectedIndex = dates.indexOfFirst { it.isSelected }
        if (selectedIndex != -1) {
            scrollState.animateScrollTo(selectedIndex * (60 + 12) - 150)
        }
    }
}

@Composable
fun DateItem(dateItem: DateItem, onClick: () -> Unit, modifier: Modifier) {
    val backgroundColor = if (dateItem.isSelected) Color(0xFF32B768) else Color(0xFFF5F5F5)
    val textColorDayOfWeek = if (dateItem.isSelected) Color.White else Color(0xFF616161)
    val textColorDay = if (dateItem.isSelected) Color.White else Color.Black

    Column(
        modifier = modifier
            .width(60.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = dateItem.dayOfWeek,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = textColorDayOfWeek,
                lineHeight = 16.sp,
            ),
        )
        Text(
            text = dateItem.day.toString(),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = textColorDay,
                lineHeight = 20.sp,
            ),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AvailableTimesSection(
    timeSlots: List<TimeSlot>,
    onTimeSelected: (Long) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            "Available times",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = Color(0xFF3C2E56),
                lineHeight = 28.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        FlowRow(
            maxItemsInEachRow = 3,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            timeSlots.forEach { timeSlot ->
                TimeSlotItem(
                    timeSlot = timeSlot,
                    onTimeSelected = { onTimeSelected(timeSlot.time) }
                )
            }
        }
    }
}

@Composable
fun TimeSlotItem(
    timeSlot: TimeSlot,
    onTimeSelected: () -> Unit
) {
    val backgroundColor = when {
        timeSlot.isSelected -> Color(0xFF32B768)
        timeSlot.isAvailable -> Color(0xFFF5F5F5)
        else -> Color(0xFFE7E7E7)
    }
    val textColor = when {
        timeSlot.isSelected -> Color.White
        timeSlot.isAvailable -> Color.Black
        else -> Color(0xFFC1C1C1)
    }

    Box(
        modifier = Modifier
            .padding(bottom = 17.dp)
            .width(110.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable(
                enabled = timeSlot.isAvailable && !timeSlot.isSelected,
                onClick = onTimeSelected
            )
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = timeSlot.time.toString(),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = textColor,
                lineHeight = 16.sp
            ),
        )
    }
}

@Composable
fun PricingSection(
    pricePerHour: String,
    onOrderClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp,
            color = Color(0xFFD4D4D4)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$pricePerHour ${stringResource(id = R.string.som_per_hour)}",
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = Color(0xFF3C2E56),
                    lineHeight = 22.05.sp
                ),
            )
            Button(
                onClick = onOrderClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff32b768)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(47.dp)
                    .width(157.dp)
            ) {
                Text(
                    text = "Order",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.White,
                        lineHeight = 16.8.sp,
                    )
                )
            }
        }
    }
}

fun LayoutCoordinates.findChildBounds(parent: LayoutCoordinates, key: String): Rect {
    return this.boundsInRoot()
}

@Preview(showBackground = true)
@Composable
private fun BookingScreenPreview() {
//    BookingScreen(
//        viewModel = hiltViewModel(),
//        onOrderClick = {}
//    )
}