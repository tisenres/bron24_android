package com.bron24.bron24_android.screens.orderdetails.layout

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType
import com.bron24.bron24_android.domain.entity.order.OrderAddress
import com.bron24.bron24_android.domain.entity.order.OrderDetails
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun OrderDetailHeader(order: OrderDetails?, modifier: Modifier = Modifier) {
    val title = buildString {
        append(order?.venueName)
        append(", Sector ")
        append(order?.sector)
    }
    Column(modifier = modifier) {
        Text(
            text = title,
            fontSize = 22.sp,
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 27.5.sp,
            color = Color(0xFF3C2E56)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "№ ${order?.orderId}",
            fontSize = 14.sp,
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Normal,
            lineHeight = 15.sp,
            color = Color(0xFF949494)
        )
        Spacer(modifier = Modifier.height(6.dp))
        OrderPrice(price = order?.cost?:"")
        Spacer(modifier = Modifier.height(6.dp))
        OrderDateTime(
            date = order?.bookingDate?:"",
            time = " ${order?.timeSlot?.startTime} - ${order?.timeSlot?.endTime}"
        )
        OrderAddress(address = "${order?.address?.district}")
    }
}


@Composable
private fun OrderPrice(price: String) {
    OrderInfoItem(
        icon = R.drawable.icon_cost,
        text ="$price ${stringResource(R.string.som)}",
        modifier = Modifier,
        contentDescription = "Price"
    )
}

@Composable
private fun OrderDateTime(date: String, time: String) {
    Row(modifier = Modifier
        .padding(bottom = 6.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        OrderInfoItem(
            icon = R.drawable.baseline_calendar_today_24,
            text = date,
            modifier = Modifier,
            contentDescription = "day"
        )
        Spacer(modifier = Modifier.width(16.dp))
        OrderInfoItem(
            icon = R.drawable.baseline_access_time_filled_24,
            text = time,
            modifier = Modifier,
            contentDescription = "time"
        )
    }
}


@Composable
private fun OrderInfoItem(
    icon: Int,
    text: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            tint = Color(0xFF949494),
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = TextStyle(fontSize = 14.sp, color = Color(0xFF949494))
        )
    }
}

@Composable
private fun OrderAddress(address: String) {
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_location_pin_24),
            contentDescription = "Location",
            tint = Color(0xFF949494),
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            modifier = Modifier.weight(0.8f),
            maxLines = 1,
            text = address,
            style = TextStyle(fontSize = 14.sp, color = Color(0xFF949494))
        )
        TextButton(
            onClick = {
                copyAddressToClipboard(context, address)
            },
            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF0067FF)),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                .height(20.dp)

        ) {
            Text(
                text = stringResource(id = R.string.copy),
                fontSize = 13.sp,
                maxLines = 1,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .drawBehind {
                        val strokeWidthPx = 1.dp.toPx()
                        val verticalOffset = size.height - 2.sp.toPx()
                        drawLine(
                            color = Color(0xFF0067FF),
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    },
            )
        }
    }
}

private fun copyAddressToClipboard(context: Context, address: String?) {
    if (address.isNullOrBlank()) {
        ToastManager.showToast(
            "No address available to copy",
            ToastType.WARNING
        )
        return
    }

    val clipboard = ContextCompat.getSystemService(context, ClipboardManager::class.java)
    val clip = ClipData.newPlainText("Venue Address", address)
    clipboard?.setPrimaryClip(clip)

    ToastManager.showToast(
        "Address copied to clipboard",
        ToastType.SUCCESS
    )
}