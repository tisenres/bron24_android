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
import androidx.compose.ui.geometry.Offset
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
fun SearchView(modifier: Modifier) {
    Box(
        modifier = modifier
            .height(120.dp)
            .fillMaxWidth()
            .background(
                color = Color(0xFF0E7B3A),
                shape = RoundedCornerShape(20.dp)
            )
            .rotate(-2f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF26A045),
                            Color(0xFF3AD461)
                        ),
                        start = Offset(0f, 500f),
                        end = Offset(500f, 1000f), // Adjust to control the gradient direction
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .rotate(2f)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
            ) {
                ProfileRow()
                SearchRow()
            }
        }
    }
}

@Composable
fun ProfileRow() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.74.dp, top = 25.36.dp, end = 22.19.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.joxon_pic),
                contentDescription = "profile_image",
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(14.92.dp))
            Text(
                text = "Salom, Joxongir aka",
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.White,
                    lineHeight = 16.8.sp
                ),
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "filter_icon",
            modifier = Modifier.size(width = 12.56.dp, height = 13.56.dp)
        )
    }
}

@Composable
fun SearchRow() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 15.dp, end = 20.dp)
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(5.dp)
            )
            .shadow(
                elevation = 10.dp,
                spotColor = Color(0x3B13733A),
            )
            .height(26.dp)
            .padding(horizontal = 10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_search_24),
            contentDescription = "search_icon",
            modifier = Modifier
                .size(11.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "Search your stadium",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = Color(0xFFA8A8A8),
                lineHeight = 12.sp
            ),
            modifier = Modifier
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSearchView() {
    SearchView(modifier = Modifier.padding(20.dp))
}