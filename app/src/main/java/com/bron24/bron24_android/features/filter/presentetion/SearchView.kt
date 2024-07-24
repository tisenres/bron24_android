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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.core.presentation.theme.interFontFamily

val gilroyFontFamily = FontFamily(
    Font(resId = R.font.gilroy_regular, weight = FontWeight.Normal),
    Font(resId = R.font.gilroy_bold, weight = FontWeight.Bold)
)

@Composable
fun SearchView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(210.dp)
            .background(
                color = Color(0xFF32B768),
                shape = RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp)
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ProfileRow()
        SearchRow()
    }
}

@Composable
fun ProfileRow() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, top = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.joxon_pic),
                contentDescription = "profile_image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(
                    text = stringResource(id = R.string.hello) + ", Cristiano Ronaldo!",
                    style = TextStyle(
                        fontFamily = interFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White,
                        lineHeight = 17.sp,
                        letterSpacing = (-0.028).em
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Toshkent viloyati",
                    style = TextStyle(
                        fontFamily = interFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.White,
                        lineHeight = 14.sp,
                        letterSpacing = (-0.028).em
                    )
                )
            }
        }
    }
}

@Composable
fun SearchRow() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 17.dp, bottom = 29.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(5.dp)
            )
            .height(50.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_search_24),
            contentDescription = "search_icon",
            modifier = Modifier
                .size(24.dp)
                .padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.width(9.dp))
        Text(
            text = "Search your stadium",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFFA8A8A8)
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSearchView() {
    SearchView()
}