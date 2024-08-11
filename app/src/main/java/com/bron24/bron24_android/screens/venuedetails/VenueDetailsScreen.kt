package com.bron24.bron24_android.screens.venuedetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em
import androidx.compose.ui.tooling.preview.Preview
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.venue.Address
import com.bron24.bron24_android.domain.entity.venue.City
import com.bron24.bron24_android.domain.entity.venue.Infrastructure
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.entity.venue.VenueOwner
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.main.theme.interFontFamily

@Composable
fun VenueDetailsScreen(
    viewModel: VenueDetailsViewModel,
    venueId: Int,
    onDismiss: () -> Unit
) {
    val venueDetails = viewModel.venueDetails.collectAsState().value
    VenueDetailsContent(details = venueDetails)
//    val sheetState = rememberBottomSheetState(
//        initialValue = SheetValue.PartiallyExpanded,
//        defineValues = {
//            SheetValue.Collapsed at height(100.dp)
//            SheetValue.PartiallyExpanded at offset(percent = 60)
//            SheetValue.Expanded at contentHeight
//        }
//    )

//    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)
//    val venueDetails = viewModel.venueDetails.collectAsState().value

//
//    BottomSheetScaffold(
//        scaffoldState = scaffoldState,
//        sheetContent = { VenueDetailsContent(details = venueDetails) },
//        content = { /* Screen content can be added here if needed */ }
//    )
}

@Composable
fun VenueDetailsContent(details: VenueDetails?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        VenueImageSection()
        Spacer(modifier = Modifier.height(15.dp))
        HeaderSection()
        Spacer(modifier = Modifier.height(27.dp))
        InfrastructureSection(details)
        Spacer(modifier = Modifier.height(15.dp))
        MapSection()
        Spacer(modifier = Modifier.height(15.dp))
        PricingSection()
    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        TitleSection()
        Spacer(modifier = Modifier.height(14.dp))
        AddressAndPhoneSection()
        Spacer(modifier = Modifier.height(14.dp))
        RatingSection()
    }
}

@Composable
fun VenueImageSection() {
    Box(
        modifier = Modifier
            .height(206.dp)
            .fillMaxWidth()
//            .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.football_field),
            contentDescription = "Venue Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        ImageOverlay()
    }
}

@Composable
fun ImageOverlay() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 11.dp, start = 24.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White)
                .padding(10.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.Black,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(10.dp)
            )
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                tint = Color.Black,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(10.dp)
            )
        }
    }
    BottomIndicators()
}

@Composable
fun BottomIndicators() {
    Row(
        modifier = Modifier
            .padding(bottom = 11.5.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(1.5.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(if (index == 0) Color.White else Color(0xFFB7B3B3))
            )
        }
    }
}

@Composable
fun TitleSection() {
    Text(
        text = "Bunyodkor kompleksi",
        style = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            color = Color(0xFF3C2E56),
            lineHeight = 29.4.sp,
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun AddressAndPhoneSection() {
    Column {
        AddressRow()
        Spacer(modifier = Modifier.height(4.dp))
        PhoneRow()
    }
}

@Composable
fun AddressRow() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_location_on_24_red),
            contentDescription = "Location",
            tint = Color(0xff949494),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = "Mustaqillik maydoni, Chilanzar, Tashkent, Uzbekistan",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFF949494),
                lineHeight = 18.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Copy",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFF0067FF),
                lineHeight = 18.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}

@Composable
fun PhoneRow() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_local_phone_24),
            contentDescription = "Phone",
            tint = Color(0xff949494),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = "+998 77 806 0278",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFF949494),
                lineHeight = 18.sp,
            )
        )
    }
}

@Composable
fun RatingSection() {
    Row {
        repeat(5) { index ->
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "Star",
                tint = Color(0xffffb800),
                modifier = Modifier.size(16.dp)
            )
            if (index < 4) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = "4.8",
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color(0xFF32B768),
                lineHeight = 18.sp,
            )
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = "(4,981)",
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color(0xFF949494),
                lineHeight = 18.sp,
            )
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = "See all review",
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color(0xFF32B768),
                lineHeight = 18.sp,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}

@Composable
fun InfrastructureSection(details: VenueDetails?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Infrastructure",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = Color(0xFF3C2E56),
                lineHeight = 29.4.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        // Infrastructure items
        // Uncomment and populate the flow row when data is available
        // FlowRow(
        //     mainAxisSpacing = 8.dp,
        //     crossAxisSpacing = 8.dp
        // ) {
        //     details?.infrastructure?.items?.forEach { item ->
        //         InfrastructureItem(item, R.drawable.ic_dollar)
        //     }
        // }
    }
}

@Composable
fun MapSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.football_field),
            contentDescription = "Map",
            modifier = Modifier
                .fillMaxWidth()
                .height(109.dp)
                .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)),
            contentScale = ContentScale.Crop
        )
        MapDetails()
    }
}

@Composable
fun MapDetails() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
    ) {
        Text(
            text = "Bunyodkor street, 18",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 17.15.sp
            ),
        )
        DistanceInfo(
            icon = R.drawable.baseline_navigation_24,
            text = "8.9 km from you",
            tintColor = Color(0xFFD9D9D9),
            rotationDegrees = -44.72f
        )
        Spacer(modifier = Modifier.height(10.dp))
        DistanceInfo(
            icon = R.drawable.ic_metro,
            text = "4th bus stop (Afrosiyob)",
            tintColor = Color(0xFFD43535),
            rotationDegrees = 0f
        )
    }
}

@Composable
fun DistanceInfo(icon: Int,
                 text: String,
                 tintColor: Color,
                 rotationDegrees: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Location",
            tint = tintColor,
            modifier = Modifier
                .size(22.dp)
                .rotate(rotationDegrees)
        )
        Text(
            text = text,
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFFB7B3B3),
                lineHeight = 18.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PricingSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "100 sum/hour",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = Color(0xFF3C2E56),
                lineHeight = 22.05.sp
            ),
        )
        Button(
            onClick = { /* Order action */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff32b768)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(47.dp)
                .width(157.dp)
        ) {
            Text(
                text = "Order",
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.White,
                    lineHeight = 16.8.sp,
                    letterSpacing = (-0.028).em
                )
            )
        }
    }
}

@Composable
fun InfrastructureItem(text: String, iconRes: Int) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(BorderStroke(1.dp, Color(0xffb8bdca)), RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            contentScale = ContentScale.Inside,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = text,
            style = TextStyle(fontSize = 14.sp, color = Color.Black),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(widthDp = 390, heightDp = 793)
@Composable
private fun VenueDetailsPreview() {
    VenueDetailsContent(
        details = VenueDetails(
            venueId = 1,
            address = Address(
                id = 6,
                addressName = "Bunyodkor street, 18",
                district = "SASASAS",
                closestMetroStation = "Novza"
            ),
            city = City(id = 5, cityName = "Tashkent"),
            infrastructure = Infrastructure(
                id = 9,
                lockerRoom = true,
                stands = "FDFDFGD",
                shower = true,
                parking = true
            ),
            venueOwner = VenueOwner(
                id = 9,
                ownerName = "Owner Name",
                tinNumber = 1223243,
                contact1 = "454545",
                contact2 = "232323"
            ),
            venueName = "Bunyodkor kompleksi",
            venueType = "Stadium",
            venueSurface = "Grass",
            peopleCapacity = 20000,
            sportType = "Football",
            pricePerHour = "100sum/hour",
            description = "A large stadium in Tashkent",
            workingHoursFrom = "9:00",
            workingHoursTill = "23:00",
            contact1 = "+998 77 806 0278",
            contact2 = "+998 77 806 0288",
            createdAt = "2021-01-01",
            updatedAt = "2023-01-01"
        )
    )
}