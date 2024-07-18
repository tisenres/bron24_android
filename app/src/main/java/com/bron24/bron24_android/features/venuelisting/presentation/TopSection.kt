//package com.bron24.bron24_android.features.venuelisting.presentation
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.bron24.bron24_android.R
//
//@Composable
//fun TopSection() {
//    Box(
//        modifier = Modifier
//            .padding(20.dp)
//            .background(
//                Brush.horizontalGradient(
//                    listOf(
//                        Color(0xFFA3E1C2),
//                        Color(0xFF69CF92)
//                    )
//                ),
//                shape = RoundedCornerShape(20.dp)
//            )
//            .padding(16.dp)
//    ) {
//        Column {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Image(
//                    painter = rememberImagePainter("https://via.placeholder.com/28x28"),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(28.dp)
//                        .clip(CircleShape)
//                        .background(Color.Transparent),
//                    contentScale = ContentScale.Crop
//                )
//                Spacer(modifier = Modifier.width(16.dp))
//                Text(
//                    text = "Salom, Cristiano Ronaldo!",
//                    style = TextStyle(
//                        fontFamily = FontFamily(Font(R.font.gilroy_regular)),
//                        fontWeight = FontWeight.Normal,
//                        fontSize = 14.sp,
//                        color = Color.White
//                    )
//                )
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            TextField(
//                value = "",
//                onValueChange = {},
//                placeholder = {
//                    Text(
//                        text = "Search your stadium",
//                        style = TextStyle(
//                            fontFamily = FontFamily(Font(R.font.gilroy_regular)),
//                            fontWeight = FontWeight.Normal,
//                            fontSize = 10.sp,
//                            color = Color(0xFFC2F5D6)
//                        )
//                    )
//                },
//                colors = TextFieldDefaults.textFieldColors(
//                    backgroundColor = Color(0xFF7BDE92),
//                    textColor = Color.White,
//                    cursorColor = Color.White
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(
//                        Color(0xFF7BDE92),
//                        shape = RoundedCornerShape(5.dp)
//                    )
//                    .padding(10.dp)
//                    .shadow(10.dp, Color(0xFF146B3A))
//            )
//        }
//    }
//}
