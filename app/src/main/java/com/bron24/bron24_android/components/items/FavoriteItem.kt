package com.bron24.bron24_android.components.items

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.GenericFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.helper.util.calculateDistance
import com.bron24.bron24_android.screens.main.theme.FavoriteItemAddress
import com.bron24.bron24_android.screens.main.theme.FavoriteItemBackGround
import com.bron24.bron24_android.screens.main.theme.FavoriteItemDivider
import com.bron24.bron24_android.screens.main.theme.FavoriteItemStadiumName
import com.bron24.bron24_android.screens.main.theme.Green
import com.bron24.bron24_android.screens.main.theme.Success
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.bgSuccess
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import org.w3c.dom.Text

@Composable
fun VenueItem(
    venue: Venue,
) {
    var isLoading by remember {
        mutableStateOf(false)
    }
    var isSelected by remember {
        mutableStateOf(false)
    }
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
            Image(
                painter = rememberAsyncImagePainter(model = venue.previewImage ?: "",
                    onLoading = {
                        isLoading = true
                    }, onSuccess = {
                        isLoading = false
                    }),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(30.dp),
                        color = Success,
                        trackColor = bgSuccess,
                        strokeWidth = 4.dp
                    )
                }
            }
            IconButton(
                onClick = { isSelected = !isSelected },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_favorite_24_red),
                    contentDescription = "", tint = if (isSelected) Red else White
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = venue.venueName,
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
                text = venue.address.addressName,
                color = FavoriteItemAddress, fontFamily = gilroyFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.weight(1f))
            Log.d("AAA", "VenueItem:${venue.distance}")
            Text(
                text = String.format("%.1f km",venue.distance),
                color = FavoriteItemAddress,
                fontFamily = FontFamily(Font(R.font.gilroy_regular))
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = FavoriteItemDivider)
        )
        Row(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_dollar),
                contentDescription = "",
                modifier = Modifier.size(20.dp),
                tint = Success
            )
            Text(
                text = "Price ${venue.pricePerHour} sum/hour",
                fontFamily = FontFamily(Font(R.font.gilroy_bold)),
                color = FavoriteItemStadiumName
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Available: ", color = FavoriteItemStadiumName,
                fontFamily = FontFamily(Font(R.font.gilroy_bold))
            )
            Text(
                text = "14 slots",
                color = Green,
                fontFamily = FontFamily(Font(R.font.gilroy_bold))
            )
        }
    }
}