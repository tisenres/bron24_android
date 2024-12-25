package com.bron24.bron24_android.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.screens.main.theme.FavoriteItemAddress
import com.bron24.bron24_android.screens.main.theme.FavoriteItemBackGround
import com.bron24.bron24_android.screens.main.theme.FavoriteItemDivider
import com.bron24.bron24_android.screens.main.theme.FavoriteItemStadiumName
import com.bron24.bron24_android.screens.main.theme.Green
import com.bron24.bron24_android.screens.main.theme.Success
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun VenueItem(
  venue: Venue
) {
  Column(
    modifier = Modifier
      .padding(top = 20.dp)
      .fillMaxWidth()
      .background(color = FavoriteItemBackGround)
  ) {
    Box(
      modifier = Modifier
        .clip(shape = RoundedCornerShape(10.dp))
        .fillMaxWidth()
        .height(160.dp)
    ) {
//            Image(
//                painter = rememberAsyncImagePainter(model = ),
//                contentDescription = "",
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
      Image(
        painter = painterResource(R.drawable.baseline_favorite_24_red),
        contentDescription = "",
        modifier = Modifier
          .padding(10.dp)
          .align(alignment = Alignment.TopEnd)
      )
    }
    Row(
      modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Bunyodkor stadioni",
        color = FavoriteItemStadiumName,
        fontFamily = gilroyFontFamily,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold
      )
      Spacer(modifier = Modifier.weight(1f))
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(R.drawable.ic_star), contentDescription = "")
        Text(text = "4.0", fontFamily = FontFamily(Font(R.font.gilroy_regular)))
      }
    }
    Row(
      modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Mustaqillik maydoni, Toshkent, Uzbekistan",
        color = FavoriteItemAddress,
        fontFamily = gilroyFontFamily,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium
      )
      Spacer(modifier = Modifier.weight(1f))
      Text(text = "3 km", color = FavoriteItemAddress, fontFamily = FontFamily(Font(R.font.gilroy_regular)))
    }
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(color = FavoriteItemDivider)
    )
    Row(
      modifier = Modifier.padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        painter = painterResource(R.drawable.ic_dollar),
        contentDescription = "",
        modifier = Modifier.size(20.dp),
        tint = Success
      )
      Text(
        text = "Price 100 sum/hour", fontFamily = FontFamily(Font(R.font.gilroy_bold)), color = FavoriteItemStadiumName
      )
      Spacer(modifier = Modifier.weight(1f))
      Text(
        text = "Available: ", color = FavoriteItemStadiumName, fontFamily = FontFamily(Font(R.font.gilroy_bold))
      )
      Text(text = "14 slots", color = Green, fontFamily = FontFamily(Font(R.font.gilroy_bold)))
    }
  }
}