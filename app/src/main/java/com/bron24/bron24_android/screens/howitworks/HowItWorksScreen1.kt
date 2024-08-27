package com.bron24.bron24_android.screens.howitworks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

//@Composable
//fun HowItWorksScreen1() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 100.dp)
//            .background(Color.White),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.select_stadium),
//            contentDescription = "Select stadium",
//            modifier = Modifier
//                .fillMaxWidth()
//                .aspectRatio(1f)
//                .padding(horizontal = 10.dp),
//            contentScale = ContentScale.FillWidth
//        )
//        Text(
//            text = stringResource(id = R.string.nearby_stadiums),
//            style = TextStyle(
//                fontFamily = gilroyFontFamily,
//                fontWeight = FontWeight.Bold,
//                fontSize = 24.sp,
//                color = Color(0xFF3C2E56),
//                lineHeight = 29.4.sp,
//            ),
//        )
//        Text(
//            text = stringResource(id = R.string.nearby_stadiums_desc),
//            style = TextStyle(
//                fontFamily = gilroyFontFamily,
//                fontWeight = FontWeight.Normal,
//                fontSize = 14.sp,
//                color = Color.Black,
//                lineHeight = 16.8.sp,
//                textAlign = TextAlign.Center,
//                letterSpacing = (-0.028).em
//            ),
//            modifier = Modifier
//                .width(331.dp)
//                .padding(top = 16.dp)
//        )
//    }
//}

@Composable
fun HowItWorksScreen1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.howitworks1),
            contentDescription = "Select stadium",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = stringResource(id = R.string.nearby_stadiums),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = Color(0xFF3C2E56),
                lineHeight = 30.sp,
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.nearby_stadiums_desc),
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color.Black,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .widthIn(max = 300.dp)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewHowItWorks1() {
    HowItWorksScreen1()
}