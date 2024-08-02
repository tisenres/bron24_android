package com.bron24.bron24_android.screens.venuedetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.R
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetState
import io.morfly.compose.bottomsheet.material3.BottomSheetScaffold
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetScaffoldState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun VenueDetailsScreen(
    viewModel: VenueDetailsViewModel,
    venueId: Int,
    onDismiss: () -> Unit
) {
    val sheetState = rememberBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        defineValues = {
            // Bottom sheet height is 100 dp.
            SheetValue.Collapsed at height(100.dp)
            // Bottom sheet offset is 60%, meaning it takes 40% of the screen.
            SheetValue.PartiallyExpanded at offset(percent = 60)
            // Bottom sheet height is equal to its content height.
            SheetValue.Expanded at contentHeight
        }
    )

    val venueDetails = viewModel.venueDetails.collectAsState().value
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            VenueDetailsContent(details = venueDetails)
        },
        content = {
            // Screen content can be added here if needed
        }
    )
}

@Composable
fun VenueDetailsContent(details: VenueDetails?) {
    H7(modifier = Modifier.fillMaxSize())
}

@Composable
fun InfrastructureItem(text: String, iconRes: Int) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .requiredWidth(width = 66.dp)
            .requiredHeight(height = 58.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .border(border = BorderStroke(0.5.dp, Color(0xffb8bdca)), shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            contentScale = ContentScale.Inside,
            modifier = Modifier.requiredSize(size = 24.dp)
        )
        Text(
            text = text,
            color = Color.Black,
            lineHeight = 10.71.em,
            style = TextStyle(fontSize = 14.sp)
        )
    }
}

@Composable
fun H7(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 390.dp)
            .requiredHeight(height = 793.dp)
            .clip(shape = RoundedCornerShape(50.dp))
            .background(color = Color.White)
    ) {
        Text(
            text = "Bunyodkor kompleksi",
            color = Color(0xff3c2e56),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 24.dp, y = 305.dp)
                .requiredWidth(width = 238.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 331.dp, y = 297.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color(0xfff6f6f6))
                .padding(all = 10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dollar),
                contentDescription = "map"
            )
        }
        Box(
            modifier = Modifier
                .requiredWidth(width = 390.dp)
                .requiredHeight(height = 290.dp)
                .clip(shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .background(color = Color.Black.copy(alpha = 0.64f))
        )
        Image(
            painter = painterResource(id = R.drawable.football_field),
            contentDescription = "image 18",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 0.dp, y = 84.dp)
                .requiredWidth(width = 390.dp)
                .requiredHeight(height = 206.dp)
                .clip(shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
        )
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 134.dp, y = 277.dp)
                .requiredWidth(width = 28.dp)
                .requiredHeight(height = 2.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .background(color = Color.White)
        )
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 165.dp, y = 277.dp)
                .requiredWidth(width = 28.dp)
                .requiredHeight(height = 2.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .background(color = Color(0xffb7b3b3))
        )
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 196.dp, y = 277.dp)
                .requiredWidth(width = 28.dp)
                .requiredHeight(height = 2.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .background(color = Color(0xffb7b3b3))
        )
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 227.dp, y = 277.dp)
                .requiredWidth(width = 28.dp)
                .requiredHeight(height = 2.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .background(color = Color(0xffb7b3b3))
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 290.dp, y = 95.dp)
                .clip(shape = RoundedCornerShape(50.dp))
                .background(color = Color.White)
                .padding(all = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_ios_share_24),
                contentDescription = "Share",
                modifier = Modifier.requiredSize(size = 16.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 338.dp, y = 95.dp)
                .clip(shape = RoundedCornerShape(50.dp))
                .background(color = Color.White)
                .padding(all = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_favorite_24_black),
                contentDescription = "Love",
                contentScale = ContentScale.Inside,
                modifier = Modifier.requiredSize(size = 16.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 24.dp, y = 95.dp)
                .clip(shape = RoundedCornerShape(50.dp))
                .background(color = Color.White)
                .padding(all = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Line arrow-left",
                modifier = Modifier.requiredSize(size = 16.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 24.dp, y = 348.dp)
                .requiredWidth(width = 351.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dollar),
                contentDescription = "location_on",
                tint = Color(0xff949494)
            )
            Text(
                lineHeight = 13.sp,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        color = Color(0xff949494),
                        fontSize = 12.sp)
                    ) { append("Mustaqillik maydoni, Chilanzar, Tashkent, Uzbekista...  ") }
                    withStyle(style = SpanStyle(
                        color = Color(0xff0067ff),
                        fontSize = 12.sp)
                    ) { append("Copy") }
                },
                modifier = Modifier.requiredWidth(width = 322.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(9.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 29.dp, y = 377.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dollar),
                contentDescription = "Phone",
                tint = Color(0xff949494),
                modifier = Modifier.requiredSize(size = 20.dp)
            )
            Text(
                text = "+998 77 806 0278",
                color = Color(0xff949494),
                lineHeight = 12.5.em,
                style = TextStyle(fontSize = 12.sp)
            )
        }
        Text(
            text = "4.8",
            color = Color(0xff32b768),
            lineHeight = 12.5.em,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 147.dp, y = 413.dp)
        )
        Text(
            text = "(4,981)",
            color = Color(0xff949494),
            lineHeight = 12.5.em,
            style = TextStyle(fontSize = 12.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 174.dp, y = 413.dp)
        )
        Text(
            text = "See all review",
            color = Color(0xff32b768),
            textDecoration = TextDecoration.Underline,
            lineHeight = 12.5.em,
            style = TextStyle(fontSize = 12.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 223.dp, y = 413.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 24.dp, y = 411.dp)
        ) {
            repeat(5) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dollar),
                    contentDescription = "Star",
                    tint = Color(0xffffb800),
                    modifier = Modifier.requiredSize(size = 20.dp)
                )
            }
        }
        Text(
            text = "Infrastructure",
            color = Color(0xff3c2e56),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 24.dp, y = 458.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 19.dp, y = 725.dp)
                .requiredWidth(width = 351.dp)
        ) {
            Text(
                text = "100sum/hour",
                color = Color(0xff3c2e56),
                style = TextStyle(fontSize = 18.sp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .requiredWidth(width = 159.dp)
                    .requiredHeight(height = 47.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = Color(0xff32b768))
                    .padding(all = 10.dp)
            ) {
                Text(
                    text = "Order",
                    color = Color.White,
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
                )
            }
        }
        StatusBarIPhoneXOrNewer()
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 24.dp, y = 502.dp)
                .requiredWidth(width = 66.dp)
                .requiredHeight(height = 58.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .border(border = BorderStroke(0.5.dp, Color(0xffb8bdca)), shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_dollar),
                contentDescription = "Lawn Care",
                contentScale = ContentScale.Inside,
                modifier = Modifier.requiredSize(size = 24.dp)
            )
            Text(
                text = "Natural",
                color = Color.Black,
                lineHeight = 10.71.em,
                style = TextStyle(fontSize = 14.sp)
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 309.dp, y = 571.dp)
                .requiredWidth(width = 66.dp)
                .requiredHeight(height = 58.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .border(border = BorderStroke(0.5.dp, Color(0xffb8bdca)), shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_dollar),
                contentDescription = "Person at Home",
                contentScale = ContentScale.Inside,
                modifier = Modifier.requiredSize(size = 24.dp)
            )
            Text(
                text = "Indoor",
                color = Color.Black,
                lineHeight = 10.71.em,
                style = TextStyle(fontSize = 14.sp)
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 309.dp, y = 502.dp)
                .requiredWidth(width = 66.dp)
                .requiredHeight(height = 58.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .border(border = BorderStroke(0.5.dp, Color(0xffb8bdca)), shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_dollar),
                contentDescription = "Parking",
                contentScale = ContentScale.Inside,
                modifier = Modifier.requiredSize(size = 24.dp)
            )
            Text(
                text = "Parking",
                color = Color.Black,
                lineHeight = 10.71.em,
                style = TextStyle(fontSize = 14.sp)
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 214.dp, y = 502.dp)
                .requiredWidth(width = 66.dp)
                .requiredHeight(height = 58.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .border(border = BorderStroke(0.5.dp, Color(0xffb8bdca)), shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_dollar),
                contentDescription = "Shower",
                contentScale = ContentScale.Inside,
                modifier = Modifier.requiredSize(size = 24.dp)
            )
            Text(
                text = "Shower",
                color = Color.Black,
                lineHeight = 10.71.em,
                style = TextStyle(fontSize = 14.sp)
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 119.dp, y = 502.dp)
                .requiredWidth(width = 66.dp)
                .requiredHeight(height = 58.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .border(border = BorderStroke(0.5.dp, Color(0xffb8bdca)), shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_dollar),
                contentDescription = "Wall Mount Camera",
                contentScale = ContentScale.Inside,
                modifier = Modifier.requiredSize(size = 24.dp)
            )
            Text(
                text = "Camera",
                color = Color.Black,
                lineHeight = 10.71.em,
                style = TextStyle(fontSize = 14.sp)
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 24.dp, y = 640.dp)
                .requiredWidth(width = 94.dp)
                .requiredHeight(height = 58.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .border(border = BorderStroke(0.5.dp, Color(0xffb8bdca)), shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_dollar),
                contentDescription = "Clock",
                contentScale = ContentScale.Inside,
                modifier = Modifier.requiredSize(size = 24.dp)
            )
            Text(
                text = "9.00 - 23.00",
                color = Color.Black,
                lineHeight = 10.71.em,
                style = TextStyle(fontSize = 14.sp)
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 24.dp, y = 571.dp)
                .requiredHeight(height = 58.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .border(border = BorderStroke(0.5.dp, Color(0xffb8bdca)), shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_dollar),
                contentDescription = "Chair",
                contentScale = ContentScale.Inside,
                modifier = Modifier.requiredSize(size = 24.dp)
            )
            Text(
                text = "Waiting room",
                color = Color.Black,
                lineHeight = 10.71.em,
                style = TextStyle(fontSize = 14.sp)
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 160.dp, y = 571.dp)
                .requiredHeight(height = 58.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .border(border = BorderStroke(0.5.dp, Color(0xffb8bdca)), shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_dollar),
                contentDescription = "Hanger",
                contentScale = ContentScale.Inside,
                modifier = Modifier.requiredSize(size = 24.dp)
            )
            Text(
                text = "Changing room",
                color = Color.Black,
                lineHeight = 10.71.em,
                style = TextStyle(fontSize = 14.sp)
            )
        }
    }
}

@Composable
fun StatusBarIPhoneXOrNewer(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 390.dp)
            .requiredHeight(height = 44.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_dollar),
            contentDescription = "Right Side",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 308.6669921875.dp, y = 17.33056640625.dp)
                .requiredWidth(width = 67.dp)
                .requiredHeight(height = 11.dp)
        )
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 24.dp, y = 12.dp)
                .requiredWidth(width = 54.dp)
                .requiredHeight(height = 21.dp)
        ) {
            DarkModeFalseTypeDefault()
        }
    }
}

@Composable
fun DarkModeFalseTypeDefault(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 54.dp)
            .requiredHeight(height = 21.dp)
            .clip(shape = RoundedCornerShape(24.dp))
    ) {
        Text(
            text = "9:41",
            color = Color(0xff181818),
            textAlign = TextAlign.Center,
            lineHeight = 1.33.em,
            style = TextStyle(fontSize = 15.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 0.dp, y = 1.dp)
                .requiredWidth(width = 54.dp)
                .requiredHeight(height = 20.dp)
        )
    }
}

@Preview(widthDp = 390, heightDp = 793)
@Composable
private fun H7Preview() {
    H7(Modifier)
}