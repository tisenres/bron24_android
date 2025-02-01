package com.bron24.bron24_android.components.items

import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.entity.order.OrderStatus
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
            text = stringResource(id = R.string.up_coming),
            isSelected = selectedOption == OrdersType.UPCOMING,
            onClick = { onClick(OrdersType.UPCOMING) },
            modifier = Modifier.weight(1f)
        )
        SegmentItem(
            text = stringResource(id = R.string.history),
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


@Composable
fun OrderCard(
    order: Order,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color(0xFFD9D9D9)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier,
        onClick = onClick,
    ) {
        Column(modifier = Modifier, verticalArrangement = Arrangement.Center) {
            OrderImage(imageUrl = order.previewImage)
            OrderContent(order = order)
        }
    }
}

@Composable
private fun OrderImage(imageUrl: String) {
//    val model = ImageRequest.Builder(LocalContext.current)
//        .data(imageUrl)
//        .placeholder(R.drawable.placeholder)
//        .build()
    var showLoading by remember {
        mutableStateOf(false)
    }
    Image(
        painter = rememberAsyncImagePainter(imageUrl, onSuccess = {
            showLoading = false
        }, onLoading = {
            showLoading = true
        }
        ),
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
        Spacer(modifier = Modifier.height(6.dp))
        OrderId(id = order.orderId)
//        OrderPrice(price = "1000")
        OrderDateTime(
            date = order.bookingDate,
            time = "${order.timeSlot.startTime} - ${order.timeSlot.endTime}"
        )
        //OrderAddress(address = order.address.addressName)
        OrderStatusItem(status = order.status)
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
            text = "$venueName $sector",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight(800),
                fontSize = 16.sp,
                color = Color(0xFF3C2E56),
                lineHeight = 19.6.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
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
        modifier = Modifier.padding(start = 1.2.dp),
        text = "№ $id",
        fontFamily = gilroyFontFamily,
        fontWeight = FontWeight.Normal,
        style = TextStyle(fontSize = 13.sp, color = Color(0xFF949494))
    )
}

@Composable
private fun OrderPrice(price: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 6.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.icon_cost),
            contentDescription = "Price",
            tint = Color(0xFF999999),
            modifier = Modifier.size(10.dp)
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = price + " ${stringResource(R.string.som)}",
            style = TextStyle(fontSize = 10.sp, color = Color(0xFF949494))
        )
    }
}

@Composable
private fun OrderDateTime(date: String, time: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp), horizontalAlignment = Alignment.Start
    ) {
        OrderInfoItem(
            icon = R.drawable.ic_date_order,
            text = date,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(7.dp))
        OrderInfoItem(
            icon = R.drawable.ic_time_order,
            text = time,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun OrderInfoItem(icon: Int, text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color(0xFF949494),
            modifier = Modifier.size(14.dp)
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = text,
            style = TextStyle(fontSize = 13.sp, color = Color(0xFF949494))
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
private fun OrderStatusItem(status: OrderStatus) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 7.dp)) {
        Box(
            modifier = Modifier
                .size(15.dp)
                .background(getStatusColor(status), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                getStatusIcon(status),
                contentDescription = "Status",
                tint = Color.White,
                modifier = Modifier
                    .size(9.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = getStatusText(status),
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(fontSize = 13.sp, color = getStatusColor(status))
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

@Composable
private fun getStatusIcon(status: OrderStatus): ImageVector {
    return when (status) {
        OrderStatus.IN_PROCESS -> ImageVector.vectorResource(id = R.drawable.ic_prof)
        OrderStatus.COMPLETED -> Icons.Default.Check
        OrderStatus.CANCELLED -> Icons.Default.Close
    }
}
@Composable
private fun getStatusText(status: OrderStatus): String {
    return when (status) {
        OrderStatus.IN_PROCESS -> stringResource(id = R.string.inprocess)
        OrderStatus.COMPLETED -> stringResource(id = R.string.completed)
        OrderStatus.CANCELLED -> stringResource(id = R.string.cancelled)
    }
}