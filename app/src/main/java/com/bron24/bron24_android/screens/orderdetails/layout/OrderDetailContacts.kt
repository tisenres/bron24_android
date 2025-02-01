package com.bron24.bron24_android.screens.orderdetails.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.order.OrderDetails
import com.bron24.bron24_android.helper.util.formatPhoneNumber
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import openDialer

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun OrderDetailContacts(order: OrderDetails?, modifier: Modifier) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        HorizontalDivider(modifier = Modifier.height(1.dp), color = Color(0xFFD4D4D4))
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.contacts),
                fontSize = 14.sp,
                color = Color.Black,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Column {
                if (order?.phoneNumber1?.isNotEmpty() == true) {

                    Text(
                        text = order.phoneNumber1.formatPhoneNumber()?:"",
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable {
                                openDialer(context, order.phoneNumber1.formatPhoneNumber() ?: "")
                            }
                            .padding(horizontal = 8.dp),
                        fontSize = 12.sp,
                        color = Color(0xff32B768),
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                }
                if (order?.phoneNumber2?.isNotEmpty() == true) {
                    Text(
                        text = order?.phoneNumber2?.formatPhoneNumber()?:"",
                        modifier = Modifier.clickable {
                            openDialer(context, order?.phoneNumber2?.formatPhoneNumber()?:"")
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


