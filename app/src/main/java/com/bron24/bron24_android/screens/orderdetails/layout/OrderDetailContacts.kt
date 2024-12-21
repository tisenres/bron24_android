package com.bron24.bron24_android.screens.orderdetails.layout

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.helper.extension.formatWithSpansPhoneNumber
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun OrderDetailContacts(order: Order, modifier: Modifier) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        HorizontalDivider(modifier = Modifier.height(1.dp), color = Color(0xFFD4D4D4))
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Contacts",
                fontSize = 14.sp,
                color = Color.Black,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Column {
                if (order.phoneNumber1.isNotEmpty()) {
                    Text(
                        text = order.phoneNumber1.formatWithSpansPhoneNumber(),
                        modifier = Modifier.clickable {
                            openDialer(context, order.phoneNumber1.formatWithSpansPhoneNumber())
                        },
                        fontSize = 12.sp,
                        color = Color(0xff32B768),
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                }
                if (!order.phoneNumber2.isNullOrEmpty()) {
                    Text(
                        text = order.phoneNumber2.formatWithSpansPhoneNumber(),
                        modifier = Modifier.clickable {
                            openDialer(context, order.phoneNumber2.formatWithSpansPhoneNumber())
                        },
                        fontSize = 12.sp,
                        color = Color(0xff32B768),
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(modifier = Modifier.height(1.dp), color = Color(0xFFD4D4D4))
    }
}

private fun openDialer(context: Context, phoneNumber: String) {
    val u = Uri.parse("tel:" + phoneNumber)
    val i = Intent(Intent.ACTION_DIAL, u)
    try {
        context.startActivity(i)
    } catch (s: Exception) {
        ToastManager.showToast("An error occurred", ToastType.ERROR)
    }
}
