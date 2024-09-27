//package com.bron24.bron24_android.screens.booking
//
//import android.app.DatePickerDialog
//import android.content.Context
//import android.os.Build
//import android.util.Log
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ExperimentalLayoutApi
//import androidx.compose.foundation.layout.FlowRow
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.bron24.bron24_android.R
//import com.bron24.bron24_android.domain.entity.booking.Sector
//import com.bron24.bron24_android.domain.entity.booking.TimeSlot
//import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Date
//import java.util.Locale
//
//@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
//@Composable
//fun BookingScreen2(
//    viewModel: BookingViewModel = hiltViewModel(),
//    venueId: Int,
//    sectors: List<String>,
//    pricePerHour: String,
//    onOrderClick: () -> Unit
//) {
//    val selectedDate by viewModel.selectedDate.collectAsState()
//    val selectedStadiumPart by viewModel.selectedStadiumPart.collectAsState()
//    val availableTimeSlots by viewModel.availableTimeSlots.collectAsState()
//    val context = LocalContext.current
//
//    LaunchedEffect(Unit) {
//        Log.d("DATA_FOR_BOOKING", venueId.toString() + " " + sectors.toString() + " " + pricePerHour)
//        viewModel.initializeBooking(venueId, sectors)
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .verticalScroll(rememberScrollState())
//            .padding(bottom = 90.dp),
//        verticalArrangement = Arrangement.spacedBy(40.dp)
//    ) {
//        StadiumPartSection(
//            sectors = sectors.map { Sector(it, it == selectedStadiumPart) },
//            onSectorSelected = { viewModel.selectStadiumPart(it) }
//        )
//
//        DateSection(
//            selectedDate = selectedDate,
//            onDateSelected = { viewModel.selectDate(it) }
//        )
//
//        AvailableTimesSection(
//            timeSlots = availableTimeSlots,
//            onTimeSelected = { /* Implement time selection if needed */ }
//        )
//
//        PricingSection(
//            pricePerHour = pricePerHour,
//            onOrderClick = onOrderClick
//        )
//    }
//}
//
//),
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis,
//        )
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(19.dp)
//        ) {
//            sectors.forEach { sector ->
//                SectorButton(
//                    sector = sector,
//                    onClick = { onSectorSelected(sector.name) }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun SectorButton(
//    sector: Sector,
//    onClick: () -> Unit
//) {
//    val backgroundColor = if (sector.isSelected) Color(0xFF32B768) else Color(0xFFF5F5F5)
//    val textColor = if (sector.isSelected) Color.White else Color.Black
//
//    Box(
//        modifier = Modifier
//            .clip(RoundedCornerShape(10.dp))
//            .background(backgroundColor)
//            .clickable(onClick = onClick)
//            .padding(vertical = 13.dp, horizontal = 29.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = "Sector ${sector.name}",
//            style = TextStyle(
//                fontFamily = gilroyFontFamily,
//                fontWeight = FontWeight.Medium,
//                fontSize = 14.sp,
//                color = textColor,
//                lineHeight = 20.sp,
//            ),
//        )
//    }
//}
//
//@Composable
//fun DateSection(
//    selectedDate: Long,
//    onDateSelected: (Long) -> Unit
//) {
//    val context = LocalContext.current
//    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
//        Text(
//            "Date",
//            style = TextStyle(
//                fontFamily = gilroyFontFamily,
//                fontWeight = FontWeight.ExtraBold,
//                fontSize = 24.sp,
//                color = Color(0xFF3C2E56),
//                lineHeight = 28.sp,
//            ),
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis,
//        )
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date(selectedDate)),
//            modifier = Modifier.clickable {
//                showDatePicker(context, selectedDate) { newDate ->
//                    onDateSelected(newDate)
//                }
//            }
//        )
//    }
//}
//
//fun showDatePicker(context: Context, initialDate: Long, onDateSelected: (Long) -> Unit) {
//    val calendar = Calendar.getInstance().apply { timeInMillis = initialDate }
//    DatePickerDialog(
//        context,
//        { _, year, month, dayOfMonth ->
//            calendar.set(year, month, dayOfMonth)
//            onDateSelected(calendar.timeInMillis)
//        },
//        calendar.get(Calendar.YEAR),
//        calendar.get(Calendar.MONTH),
//        calendar.get(Calendar.DAY_OF_MONTH)
//    ).show()
//}
//
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun AvailableTimesSection(
//    timeSlots: List<TimeSlot>,
//    onTimeSelected: (TimeSlot) -> Unit
//) {
//    Column(
//        modifier = Modifier.padding(horizontal = 20.dp),
//        verticalArrangement = Arrangement.spacedBy(20.dp)
//    ) {
//        Text(
//            "Available times",
//            style = TextStyle(
//                fontFamily = gilroyFontFamily,
//                fontWeight = FontWeight.ExtraBold,
//                fontSize = 24.sp,
//                color = Color(0xFF3C2E56),
//                lineHeight = 28.sp,
//            ),
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis,
//        )
//        FlowRow(
//            maxItemsInEachRow = 3,
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//        ) {
//            timeSlots.forEach { timeSlot ->
//                TimeSlotItem(
//                    timeSlot = timeSlot,
//                    onTimeSelected = { onTimeSelected(timeSlot) }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun TimeSlotItem(
//    timeSlot: TimeSlot,
//    onTimeSelected: () -> Unit
//) {
//    val backgroundColor = when {
//        timeSlot.isSelected -> Color(0xFF32B768)
//        timeSlot.isAvailable -> Color(0xFFF5F5F5)
//        else -> Color(0xFFE7E7E7)
//    }
//    val textColor = when {
//        timeSlot.isSelected -> Color.White
//        timeSlot.isAvailable -> Color.Black
//        else -> Color(0xFFC1C1C1)
//    }
//
//    Box(
//        modifier = Modifier
//            .padding(bottom = 17.dp)
//            .width(110.dp)
//            .clip(RoundedCornerShape(10.dp))
//            .background(backgroundColor)
//            .clickable(
//                enabled = timeSlot.isAvailable && !timeSlot.isSelected,
//                onClick = onTimeSelected
//            )
//            .padding(vertical = 8.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = formatTimeSlot(timeSlot.startTime, timeSlot.endTime),
//            style = TextStyle(
//                fontFamily = gilroyFontFamily,
//                fontWeight = FontWeight.Normal,
//                fontSize = 12.sp,
//                color = textColor,
//                lineHeight = 16.sp
//            ),
//        )
//    }
//}
//
//@Composable
//fun PricingSection(
//    pricePerHour: String,
//    onOrderClick: () -> Unit
//) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        HorizontalDivider(
//            modifier = Modifier.fillMaxWidth(),
//            thickness = 0.5.dp,
//            color = Color(0xFFD4D4D4)
//        )
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(70.dp)
//                .padding(horizontal = 16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = "$pricePerHour ${stringResource(id = R.string.som_per_hour)}",
//                style = TextStyle(
//                    fontFamily = gilroyFontFamily,
//                    fontWeight = FontWeight.ExtraBold,
//                    fontSize = 18.sp,
//                    color = Color(0xFF3C2E56),
//                    lineHeight = 22.05.sp
//                ),
//            )
//            Button(
//                onClick = onOrderClick,
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff32b768)),
//                shape = RoundedCornerShape(10.dp),
//                modifier = Modifier
//                    .height(47.dp)
//                    .width(157.dp)
//            ) {
//                Text(
//                    text = "Order",
//                    style = TextStyle(
//                        fontFamily = gilroyFontFamily,
//                        fontWeight = FontWeight.Normal,
//                        fontSize = 14.sp,
//                        color = Color.White,
//                        lineHeight = 16.8.sp,
//                    )
//                )
//            }
//        }
//    }
//}
//
//fun formatTimeSlot(startTime: String, endTime: String): String {
//    return "$startTime - $endTime"
//}