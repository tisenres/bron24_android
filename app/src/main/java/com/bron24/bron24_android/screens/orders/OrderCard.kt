package com.bron24.bron24_android.screens.orders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.entity.order.OrderStatus


@Composable
fun OrderCard(order: Order, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color(0xFFD9D9D9)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier,
        onClick = onClick
    ) {
        Column {
            OrderImage(imageUrl = order.previewImage)
            OrderContent(order = order)
        }
    }
}

@Composable
private fun OrderImage(imageUrl: String) {
    val model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .placeholder(R.drawable.placeholder)
        .build()
    Image(
        painter = rememberAsyncImagePainter(model),
        contentDescription = "Venue image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(127.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    )
}

@Composable
private fun OrderContent(order: Order) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OrderHeader(venueName = order.venueName, sector = order.sector)
        Spacer(modifier = Modifier.height(2.dp))
        OrderId(id = order.id.toString())
        Spacer(modifier = Modifier.height(10.dp))
        OrderPrice(price = order.cost)
        Spacer(modifier = Modifier.height(5.dp))
        OrderDateTime(
            date = order.bookingDate,
            time = " ${order.timeSlot.startTime} - ${order.timeSlot.endTime}"
        )
        Spacer(modifier = Modifier.height(5.dp))
        OrderAddress(address = order.address.addressName)
        Spacer(modifier = Modifier.height(7.dp))
        OrderStatus(status = order.status)
    }
}

@Composable
private fun OrderHeader(venueName: String, sector: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$venueName, $sector",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF3C2E56),
                fontWeight = FontWeight.Bold
            )
        )

        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "ArrowRight",
            modifier = Modifier.size(17.dp)
        )
    }
}

@Composable
private fun OrderId(id: String) {
    Text(
        text = "№ $id",
        style = TextStyle(fontSize = 10.sp, color = Color(0xFF949494))
    )
}

@Composable
private fun OrderPrice(price: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.icon_cost),
            contentDescription = "Price",
            tint = Color(0xFF999999),
            modifier = Modifier.size(10.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = price + " ${stringResource(R.string.som)}",
            style = TextStyle(fontSize = 10.sp, color = Color(0xFF949494))
        )
    }
}

@Composable
private fun OrderDateTime(date: String, time: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        OrderInfoItem(
            icon = R.drawable.baseline_calendar_today_24,
            text = date,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.width(8.dp))
        OrderInfoItem(
            icon = R.drawable.baseline_access_time_filled_24,
            text = time,
            modifier = Modifier
        )
    }
}

@Composable
private fun OrderInfoItem(icon: Int, text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color(0xFF949494),
            modifier = Modifier.size(10.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = TextStyle(fontSize = 10.sp, color = Color(0xFF949494))
        )
    }
}

@Composable
private fun OrderAddress(address: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_location_pin_24),
            contentDescription = "Location",
            tint = Color(0xFF949494),
            modifier = Modifier.size(10.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = address,
            style = TextStyle(fontSize = 10.sp, color = Color(0xFF949494))
        )
    }
}

@Composable
private fun OrderStatus(status: OrderStatus) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(getStatusColor(status), CircleShape)
        ) {
            Icon(
                getStatusIcon(status),
                contentDescription = "Status",
                tint = Color.White,
                modifier = Modifier
                    .size(9.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = getStatusText(status),
            style = TextStyle(fontSize = 12.sp, color = getStatusColor(status))
        )
    }
}

private fun getStatusColor(status: OrderStatus): Color {
    return when (status) {
        OrderStatus.IN_PROCESS -> Color(0xFF3DDA7E)
        OrderStatus.COMPLETED -> Color(0xFF32B768)
        OrderStatus.CANCELLED -> Color(0xFFDC2626)
    }
}

private fun getStatusIcon(status: OrderStatus): ImageVector {
    return when (status) {
        OrderStatus.IN_PROCESS -> Icons.Default.Check
        OrderStatus.COMPLETED -> Icons.Default.Close
        OrderStatus.CANCELLED -> Icons.Default.Close
    }
}

private fun getStatusText(status: OrderStatus): String {
    return when (status) {
        OrderStatus.IN_PROCESS -> "In process"
        OrderStatus.COMPLETED -> "Completed"
        OrderStatus.CANCELLED -> "Cancelled"
    }
}