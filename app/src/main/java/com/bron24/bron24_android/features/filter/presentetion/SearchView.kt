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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

@Composable
fun SearchView() {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .size(340.dp, 152.dp)
            .background(
                color = Color(0xFF33B85C),
                shape = RoundedCornerShape(20.dp)
            )
            .offset(x = 1.dp, y = -3.65.dp)
            .rotate(-1.25f)
    ) {
        Box(
            modifier = Modifier
                .size(340.dp, 152.dp)
                .background(
                    brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFA4EEC2),
                            Color(0xFF69CF91)
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .offset(x = -0.92.dp, y = 3.66.dp)
                .rotate(1.25f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .offset(y = (-39.03).dp)
                        .rotate(-1f)
                        .padding(15.dp)
                ) {
                    Image(
                        painter = rememberImagePainter("https://via.placeholder.com/28x28"),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Salom, Cristiano Ronaldo!",
                        fontSize = 14.sp,
                        color = Color.White
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
                        .offset(y = 23.83.dp)
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
    SearchView()
}