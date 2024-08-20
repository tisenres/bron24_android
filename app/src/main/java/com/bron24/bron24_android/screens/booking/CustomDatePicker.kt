package com.bron24.bron24_android.screens.booking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomDatePicker(
    dates: List<LocalDate>,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            // Calculate the closest item to snap to when scrolling stops
            val closestIndex = (listState.firstVisibleItemIndex + (listState.firstVisibleItemScrollOffset / 100f)).roundToInt()
            coroutineScope.launch {
                listState.animateScrollToItem(closestIndex)
                onDateSelected(dates[closestIndex])
            }
        }
    }

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(horizontal = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(dates) { index, date ->
            val isSelected = date == selectedDate
            val centerOffset = listState.layoutInfo.visibleItemsInfo.size / 2
            val distanceFromCenter = (index - centerOffset).absoluteValue

            // Calculate Y offset and opacity based on distance from center
            val yOffset by animateDpAsState(targetValue = (distanceFromCenter * 20).dp)
            val opacity by animateFloatAsState(targetValue = 1f - (distanceFromCenter * 0.15f))

            DateItem(
                date = date,
                isSelected = isSelected,
                yOffset = yOffset,
                opacity = opacity,
                onClick = { onDateSelected(date) }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateItem(
    date: LocalDate,
    isSelected: Boolean,
    yOffset: Dp,
    opacity: Float,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .graphicsLayer {
                translationY = yOffset.toPx()
                alpha = opacity
            }
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color(0xFF4CAF50) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = date.dayOfWeek.name.take(3), // e.g., "Mon", "Tue"
            color = if (isSelected) Color.White else Color.Black,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = date.dayOfMonth.toString(),
            color = if (isSelected) Color.White else Color.Black,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DatePickerPreview() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val dates = remember {
        List(14) { LocalDate.now().minusDays(7).plusDays(it.toLong()) }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Date",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp),
                color = Color(0xFF512DA8),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            CustomDatePicker(
                dates = dates,
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it }
            )
        }
    }
}