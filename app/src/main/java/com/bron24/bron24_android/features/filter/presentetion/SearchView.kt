package com.bron24.bron24_android.features.filter.presentetion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
            .size(340.dp, 145.dp)
            .background(
                color = Color(0xFF32B768),
                shape = RoundedCornerShape(20.dp)
            )
            .rotate(-1.25f)
    ) {
        Box(
            modifier = Modifier
                .size(340.dp, 145.dp)
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .rotate(0.25f)
                        .padding(15.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ronaldo),
                        contentDescription = "profile_image",
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "Salom, Crisitano Ronaldo!",
                        modifier = Modifier
                            .padding(start = 24.dp)
                            .height(32.dp)
                            .width(78.dp),
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            lineHeight = 16.8.sp
                        ),
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .background(
                            color = Color(0xFF7CDDA3),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .shadow(10.dp, RoundedCornerShape(5.dp))
                        .rotate(-1f)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "Search your stadium",
                        fontSize = 10.sp,
                        color = Color(0xFFC2F5D6)
                    )
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
