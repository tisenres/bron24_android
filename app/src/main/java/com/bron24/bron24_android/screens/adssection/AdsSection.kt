import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.interFontFamily
import kotlinx.coroutines.delay

@Composable
fun AdsSection(modifier: Modifier = Modifier) {
    var currentPage by remember { mutableIntStateOf(0) }
    val imagesCount = 4 // Update this with the actual number of images
    val autoScrollInterval = 10000L // 10 seconds in milliseconds

    // Automatically switch images every 10 seconds
    LaunchedEffect(key1 = currentPage) {
        while (true) {
            delay(autoScrollInterval)
            currentPage = (currentPage + 1) % imagesCount
        }
    }

    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.special_offers),
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight(600),
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    color = Color.Black
                )
            )
            Text(
                text = stringResource(id = R.string.see_all),
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight(600),
                    fontSize = 12.sp,
                    lineHeight = 15.sp,
                    color = Color(0xFF32B768)
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(16.dp))
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (dragAmount > 0) {
                            currentPage = (currentPage - 1).coerceAtLeast(0)
                        } else {
                            currentPage = (currentPage + 1) % imagesCount
                        }
                    }
                }
        ) {
            OfferImage(currentPage)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(imagesCount) { index ->
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = if (currentPage == index) Color(0xFF32B768) else Color(
                                0xFFD9D9D9
                            ),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Composable
fun OfferImage(page: Int) {
    val imageRes = when (page) {
        0 -> R.drawable.offer_image_1
        1 -> R.drawable.joxon_pic
        2 -> R.drawable.football_field
        else -> R.drawable.select_stadium
    }
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "offer_image",
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop
    )
}