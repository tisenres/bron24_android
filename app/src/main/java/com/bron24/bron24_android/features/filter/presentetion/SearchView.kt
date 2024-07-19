package com.bron24.bron24_android.features.filter.presentetion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R

val gilroyFontFamily = FontFamily(
    Font(resId = R.font.gilroy_regular, weight = FontWeight.Normal),
    Font(resId = R.font.gilroy_bold, weight = FontWeight.Bold)
)

@Composable
fun SearchView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(337.97.dp, 144.57.dp)
            .background(
                color = Color(0xFF32B768),
                shape = RoundedCornerShape(20.dp)
            )
            .rotate(-1.25f)
    ) {
        Box(
            modifier = Modifier
                .size(337.97.dp, 144.57.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFA4ECC3),
                            Color(0xFF69CE91)
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .rotate(1.25f)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(start = 18.74.dp, top = 25.36.dp)
                        .rotate(0.25f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ronaldo),
                        contentDescription = "profile_image",
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(14.92.dp))
                    Text(
                        text = "Salom, Cristiano Ronaldo!",
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color.White,
                            lineHeight = 16.8.sp
                        ),
                    )
                    Spacer(modifier = Modifier.width(87.3.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "filter_icon",
                        modifier = Modifier.size(width = 12.56.dp, height = 13.56.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(horizontal = 18.94.dp, vertical = 34.5.dp)
                        .background(
                            color = Color(0xFF7ADDA2),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(5.dp),
                            ambientColor = Color(0xFF14733B).copy(alpha = 0.23f),
                            spotColor = Color(0xFF14733B).copy(alpha = 0.23f)
                        )
                        .rotate(0.25f)
                        .size(width = 310.96.dp, height = 26.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "search_icon",
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(8.96.dp))
                        Text(
                            text = "Search your stadium",
                            style = TextStyle(
                                fontFamily = gilroyFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 10.sp,
                                color = Color(0xFFC2F5D7),
                                lineHeight = 12.sp
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSearchView() {
    SearchView(modifier = Modifier.padding(20.dp))
}
