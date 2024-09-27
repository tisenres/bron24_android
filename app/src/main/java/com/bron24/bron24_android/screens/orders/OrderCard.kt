package com.bron24.bron24_android.screens.orders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R

data class OrderDetails(
    val id: String,
    val venueName: String,
    val sector: String,
    val date: String,
    val time: String,
    val price: String,
    val address: String,
    val status: OrderStatus,
    val imageRes: Int
)

enum class OrderStatus {
    IN_PROCESS, COMPLETED, CANCELLED
}

@Composable
fun OrderCard(order: OrderDetails, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color(0xFFD9D9D9)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .width(342.dp)
            .height(252.dp)
    ) {
        Column {
            OrderImage(imageRes = order.imageRes)
            OrderContent(order = order)
        }
    }
}

@Composable
private fun OrderImage(imageRes: Int) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Venue image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(127.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    )
}

@Composable
private fun OrderContent(order: OrderDetails) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OrderHeader(venueName = order.venueName, sector = order.sector)
        Spacer(modifier = Modifier.height(17.dp))
        OrderId(id = order.id)
        Spacer(modifier = Modifier.height(5.dp))
        OrderPrice(price = order.price)
        Spacer(modifier = Modifier.height(5.dp))
        OrderDateTime(date = order.date, time = order.time)
        Spacer(modifier = Modifier.height(5.dp))
        OrderAddress(address = order.address)
        Spacer(modifier = Modifier.weight(1f))
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
            style = TextStyle(fontSize = 14.sp, color = Color(0xFF3C2E56), fontWeight = FontWeight.Bold)
        )
        Icon(
            painter = painterResource(id = R.drawable.promo_code),
            contentDescription = "Menu",
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
            painter = painterResource(id = R.drawable.promo_code),
            contentDescription = "Price",
            tint = Color(0xFF999999),
            modifier = Modifier.size(10.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = price,
            style = TextStyle(fontSize = 10.sp, color = Color(0xFF949494))
        )
    }
}

@Composable
private fun OrderDateTime(date: String, time: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        OrderInfoItem(
            icon = R.drawable.promo_code,
            text = date,
            modifier = Modifier.weight(1f)
        )
        OrderInfoItem(
            icon = R.drawable.promo_code,
            text = time,
            modifier = Modifier.weight(1f)
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
            painter = painterResource(id = R.drawable.promo_code),
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
                .size(12.dp)
                .background(getStatusColor(status), CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.promo_code),
                contentDescription = "Status",
                tint = Color.White,
                modifier = Modifier
                    .size(6.dp)
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

private fun getStatusText(status: OrderStatus): String {
    return when (status) {
        OrderStatus.IN_PROCESS -> "In process"
        OrderStatus.COMPLETED -> "Completed"
        OrderStatus.CANCELLED -> "Cancelled"
    }
}

@Preview(widthDp = 342, heightDp = 252)
@Composable
private fun OrderCardPreview() {
    val sampleOrder = OrderDetails(
        id = "636582",
        venueName = "Bunyodkor kompleksi",
        sector = "Sector A",
        date = "21.02.2024",
        time = "9:00 - 10:00",
        price = "50 000 sum",
        address = "Mustaqillik maydoni, Chilanzar, Tashkent",
        status = OrderStatus.IN_PROCESS,
        imageRes = R.drawable.promo_code
    )
    OrderCard(order = sampleOrder)
}