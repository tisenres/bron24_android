package com.bron24.bron24_android.screens.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun OrdersTabRow(
    modifier: Modifier = Modifier,
    selectedOption: OrdersType,
    onClick: (OrdersType) -> Unit
) {

    Row(
        modifier = modifier
            .height(48.dp)
            .clip(CircleShape)
            .border(width = 1.dp, color = Color(0xFFD9D9D9), shape = CircleShape) // Stroke (border)
            .background(Color(0xFFF5F5F5)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SegmentItem(
            text = "Upcoming",
            isSelected = selectedOption == OrdersType.UPCOMING,
            onClick = { onClick(OrdersType.UPCOMING) },
            modifier = Modifier.weight(1f)
        )
        SegmentItem(
            text = "History",
            isSelected = selectedOption == OrdersType.HISTORY,
            onClick = { onClick(OrdersType.HISTORY) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SegmentItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp)
            .clip(CircleShape)
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = Color(0xFFE4E4E4),
                shape = CircleShape
            )
            .background(if (isSelected) Color.White else Color(0xFFF5F5F5))
            .clickable(onClick = onClick, indication = null, interactionSource = null),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.Black else Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = gilroyFontFamily
        )
    }
}

enum class OrdersType {
    UPCOMING,
    HISTORY
}
