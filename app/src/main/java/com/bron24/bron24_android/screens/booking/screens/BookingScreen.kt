package com.bron24.bron24_android.screens.booking.screens

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.bron24.bron24_android.screens.booking.states.BookingState
import com.bron24.bron24_android.screens.booking.BookingViewModel
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun BookingScreen(
    viewModel: BookingViewModel = hiltViewModel(),
    venueId: Int,
    sectors: List<String>,
    pricePerHour: String,
    onOrderClick: () -> Unit
) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedSector by viewModel.selectedSector.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val totalPrice by viewModel.totalPrice.collectAsState()
    val getAvailableTimesState by viewModel.getAvailableTimesState.collectAsState()
    val availableTimeSlots by viewModel.availableTimeSlots.collectAsState()

    val sectorEnums = sectors.mapNotNull { sectorString ->
        try {
            Sector.valueOf(sectorString)
        } catch (e: IllegalArgumentException) {
            null // In case the string does not match any enum
        }
    }

    LaunchedEffect(Unit) {
        viewModel.selectDate(System.currentTimeMillis())
        if (sectorEnums.isNotEmpty()) {
            viewModel.selectSector(sectorEnums.first())
        }
    }

    LaunchedEffect(key1 = venueId, key2 = selectedDate, key3 = selectedSector) {
        viewModel.getAvailableTimes(venueId)
        Log.d("BookingScreen", "State after fetching: $getAvailableTimesState")
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
                sectors = sectorEnums, // Send Sector enums directly
                selectedSector = selectedSector,
                onSectorSelected = { viewModel.selectSector(it) }
            )

            DateSection(
                dates = viewModel.availableDates.collectAsState().value,
                visibleMonthYear = viewModel.visibleMonthYear.collectAsState().value,
                onDateSelected = { timestamp -> viewModel.selectDate(timestamp) },
                scrollState = scrollState,
                onMonthClick = { viewModel.showDatePicker() },
                onVisibleDatesChanged = {}
            )

            AvailableTimesSection(
                timeSlots = availableTimeSlots,
                onTimeSelected = viewModel::selectTimeSlot
            )
        }

        PricingSection(
            totalPrice = totalPrice.toString(),
            onOrderClick = onOrderClick,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    // Booking state handling
    when (getAvailableTimesState) {
        is BookingState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
        is BookingState.Error -> {
            Text("Error: ${(getAvailableTimesState as BookingState.Error).message}")
        }
        is BookingState.Success -> {
            // Handle success (e.g., show success message or perform navigation)
        }
        BookingState.Idle -> {}
        BookingState.Cancelled -> {}
    }

    // DatePicker dialog handling
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
    sectors: List<Sector>,  // Update the type to List<Sector>
    selectedSector: Sector?,
    onSectorSelected: (Sector) -> Unit
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
                    sectorName = sector.name, // Use the name of the enum
                    onClick = { onSectorSelected(sector) },
                    isSelected = sector == selectedSector
                )
            }
        }
    }
}

// Other functions remain unchanged
// ...

@Composable
fun SectorButton(
    sectorName: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    val backgroundColor = if (isSelected) Color(0xFF32B768) else Color(0xFFF5F5F5)
    val textColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(vertical = 13.dp, horizontal = 29.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = sectorName,
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
//    val coroutineScope = rememberCoroutineScope()

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

@Composable
fun PricingSection(
    totalPrice: String,
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
                text = "$totalPrice ${stringResource(id = R.string.som_per_hour)}",
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AvailableTimesSection(
    timeSlots: List<TimeSlot>,
    onTimeSelected: (TimeSlot) -> Unit
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
                    onTimeSelected = { onTimeSelected(timeSlot) }
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
        // Assuming timeSlot.startTime and timeSlot.endTime are strings.
        val startTimeInMillis = parseTimeString(timeSlot.startTime)
        val endTimeInMillis = parseTimeString(timeSlot.endTime)

        Text(
            text = formatTimeSlot(startTimeInMillis, endTimeInMillis),
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

fun parseTimeString(time: String): Long {
    // Parse "HH:mm:ss" to milliseconds since epoch
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val date = timeFormat.parse(time) ?: throw IllegalArgumentException("Invalid time format")
    return date.time
}

fun LayoutCoordinates.findChildBounds(parent: LayoutCoordinates, key: String): Rect {
    return this.boundsInRoot()
}

fun formatTimeSlot(startTime: Long, endTime: Long): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return "${dateFormat.format(startTime)} - ${dateFormat.format(endTime)}"
}

@Preview(showBackground = true)
@Composable
private fun BookingScreenPreview() {
//    BookingScreen(
//        viewModel = hiltViewModel(),
//        onOrderClick = {}
//    )
}