package com.bron24.bron24_android.screens.booking.screens.startbooking

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType
import com.bron24.bron24_android.domain.entity.booking.DateItem
import com.bron24.bron24_android.domain.entity.booking.Sector
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.helper.util.priceToInt
import com.bron24.bron24_android.screens.booking.states.BookingState
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import java.util.Calendar

@Composable
fun BookingScreen(
    viewModel: BookingViewModel = hiltViewModel(),
    venueId: Int,
    venueName: String,
    sectors: List<String>,
    pricePerHour: String,
    onOrderClick: (Int, String, String, String, List<TimeSlot>) -> Unit
) {

    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedSector by viewModel.selectedSector.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()
    val selectedDateIndex by viewModel.selectedDateIndex.collectAsState()
    val availableTimeSlots by viewModel.availableTimeSlots.collectAsState()

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val getAvailableTimesState by viewModel.getAvailableTimesState.collectAsState()
    val selectedTimeSlots by viewModel.selectedTimeSlots.collectAsState()
    val visibleMonthYear by viewModel.visibleMonthYear.collectAsState()

    val showDatePicker by viewModel.showDatePicker.collectAsState()

    var isOrderEnabled by remember { mutableStateOf(false) }
    val sectorEnums = sectors.map { sectorString ->
        Sector.valueOf(sectorString)
    }

    LaunchedEffect(selectedTimeSlots) {
        isOrderEnabled = selectedTimeSlots.isNotEmpty()
    }

    LaunchedEffect(showDatePicker) {
        if (showDatePicker) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selectedDate

            val maxDate = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, 29)
            }

            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    viewModel.selectDate(calendar.timeInMillis)
                    viewModel.onDatePickerDismissed()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                datePicker.minDate = System.currentTimeMillis() - 1000
                datePicker.maxDate = maxDate.timeInMillis
                setOnDismissListener { viewModel.onDatePickerDismissed() }
            }

            datePickerDialog.show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.selectDate(System.currentTimeMillis())
        if (sectorEnums.isNotEmpty()) {
            viewModel.selectSector(sectorEnums.first())
        }
        viewModel.setPricePerHour(pricePerHour.priceToInt())
        viewModel.generateAvailableDates()
    }

    LaunchedEffect(key1 = selectedDate, key2 = selectedSector) {
        viewModel.getAvailableTimes(venueId)
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
                sectors = sectorEnums,
                selectedSector = selectedSector,
                onSectorSelected = { viewModel.selectSector(it) }
            )

            DateSection(
                dates = viewModel.availableDates.collectAsState().value,
                visibleMonthYear = visibleMonthYear,
                onDateSelected = { timestamp -> viewModel.selectDate(timestamp) },
                scrollState = scrollState,
                onMonthClick = {
                    viewModel.showDatePicker()
                },
                selectedDateIndex = selectedDateIndex
            )

            AvailableTimesSection(
                timeSlots = availableTimeSlots,
                onTimeSelected = viewModel::selectTimeSlot,
                getAvailableTimesState = getAvailableTimesState,
                selectedTimeSlot = selectedTimeSlots
            )
        }

        PricingSection(
            totalPrice = totalPrice,
            onOrderClick = {
                onOrderClick(
                    venueId,
                    venueName,
                    millisToDate(selectedDate),
                    selectedSector?.name ?: "X",
                    selectedTimeSlots.toList()
                )
            },
            modifier = Modifier.align(Alignment.BottomCenter),
            isOrderEnabled = isOrderEnabled
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StadiumPartSection(
    sectors: List<Sector>,
    selectedSector: Sector?,
    onSectorSelected: (Sector) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.stadium_part),
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            sectors.forEach { sector ->
                SectorButton(
                    sectorName = stringResource(sector.displayNameRes),
                    onClick = { onSectorSelected(sector) },
                    isSelected = sector == selectedSector
                )
            }
        }
    }
}

@Composable
fun SectorButton(
    sectorName: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.tertiary else Color(0xFFF5F5F5)
    val textColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .widthIn(min = 110.dp, max = 180.dp)
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
    scrollState: ScrollState,
    selectedDateIndex: Int
) {

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .padding(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.date),
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
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(
                        onClick = {
                            onMonthClick()
                        }
                    )
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_calendar_today_24),
                    contentDescription = "Calendar",
                    modifier = Modifier.size(16.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF949494)),
                )
                Text(
                    text = visibleMonthYear,
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
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
        ) {
            dates.forEachIndexed { index, dateItem ->
                DateItem(
                    dateItem = dateItem,
                    onClick = {
                        onDateSelected(dateItem.timestamp)
                    },
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        coordinates.boundsInRoot().let { bounds ->
                            bounds.left >= 0 && bounds.right <= coordinates.size.width
                        }
                    }
                )
            }
        }
    }

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    LaunchedEffect(selectedDateIndex) {
        if (selectedDateIndex != -1) {
            val dateItemWidth = 60.dp
            val dateItemSpacing = 12.dp
            val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
            val dateItemWidthPx = with(density) { dateItemWidth.toPx() }
            val dateItemSpacingPx = with(density) { dateItemSpacing.toPx() }

            val scrollPosition = selectedDateIndex * (dateItemWidthPx + dateItemSpacingPx)
            val targetScrollPosition =
                (scrollPosition - (screenWidthPx / 2) + (dateItemWidthPx / 2)).toInt()

            scrollState.animateScrollTo(targetScrollPosition)
        }
    }
}

@Composable
fun DateItem(dateItem: DateItem, onClick: () -> Unit, modifier: Modifier) {
    val backgroundColor = if (dateItem.isSelected) MaterialTheme.colorScheme.tertiary else Color(0xFFF5F5F5)
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
    modifier: Modifier = Modifier,
    isOrderEnabled: Boolean
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
                text = "$totalPrice ${stringResource(id = R.string.som)}",
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isOrderEnabled) MaterialTheme.colorScheme.tertiary else Color(0xFFE4E4E4),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFE4E4E4),
                    disabledContentColor = Color.Gray
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(47.dp)
                    .widthIn(min = 130.dp, max = 170.dp),
                enabled = isOrderEnabled
            ) {
                Text(
                    text = stringResource(id = R.string.order),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
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
    selectedTimeSlot: Set<TimeSlot>,
    onTimeSelected: (TimeSlot) -> Unit,
    getAvailableTimesState: BookingState,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.available_times),
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
        when (getAvailableTimesState) {
            is BookingState.Loading -> {
                LoadingScreen()
            }

            is BookingState.Error -> {
                ToastManager.showToast(
                    "Error: ${(getAvailableTimesState).message}",
                    ToastType.ERROR
                )
            }

            is BookingState.Success -> {
                if (timeSlots.isEmpty()) {
                    EmptyTimeSlots()
                } else {
                    FlowRow(
                        maxItemsInEachRow = 3,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        timeSlots.forEach { timeSlot ->
                            TimeSlotItem(
                                timeSlot = timeSlot,
                                onTimeSelected = {
                                    if (selectedTimeSlot.contains(timeSlot)) {
                                        onTimeSelected(timeSlot)
                                    } else if (selectedTimeSlot.size < 2) onTimeSelected(timeSlot)
                                }
                            )
                        }
                    }
                }
            }

            BookingState.Idle -> {
                LoadingScreen()
            }

            BookingState.Cancelled -> {}
        }
    }
}

@Composable
fun EmptyTimeSlots() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.sad_ball),
            contentDescription = "No available time slots",
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = stringResource(id = R.string.empty_info),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color(0xFF949494),
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun TimeSlotItem(
    timeSlot: TimeSlot,
    onTimeSelected: () -> Unit,
) {
    val backgroundColor = when {
        timeSlot.isSelected -> MaterialTheme.colorScheme.tertiary
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
//            .padding(bottom = 15.dp, end = 13.dp)
            .fillMaxWidth(0.3f)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable(
                enabled = timeSlot.isAvailable,
                onClick = onTimeSelected
            )
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        val startTimeInMillis = timeSlot.startTime
        val endTimeInMillis = timeSlot.endTime

        Text(
            text = "$startTimeInMillis - $endTimeInMillis",
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
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

fun millisToDate(timeMillis: Long): String {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = timeMillis
    }

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return String.format("%04d-%02d-%02d", year, month, day)
}

@Preview(showBackground = true)
@Composable
private fun BookingScreenPreview() {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable(onClick = { }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.date),
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
                "October 2024",
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
    }
}