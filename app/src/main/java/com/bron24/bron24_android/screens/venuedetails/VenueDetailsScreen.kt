package com.bron24.bron24_android.screens.venuedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.bron24.bron24_android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenueDetailsScreen(viewModel: VenueDetailsViewModel, venueId: Int) {
    val venueDetails = viewModel.venueDetails.collectAsState().value

    LaunchedEffect(venueId) {
        viewModel.fetchVenueDetails(venueId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle share */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = { /* Handle favorite */ }) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorite")
                    }
                }
            )
        }
    ) { paddingValues ->
        venueDetails?.let { details ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Venue Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(details),
                        contentDescription = "Venue Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                // Venue Details
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(
                        text = details.venueName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = details.address.addressName,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = details.contact1,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Rating
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "4.5",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        // Add star rating component here
                        Text(
                            text = "12",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Infrastructure",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Infrastructure items
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfrastructureItem("Natural", R.drawable.ic_dollar)
                        InfrastructureItem("Camera", R.drawable.ic_dollar)
                        InfrastructureItem("Shower", R.drawable.ic_dollar)
                        InfrastructureItem("Parking", R.drawable.ic_dollar)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfrastructureItem("Waiting room", R.drawable.ic_dollar)
                        InfrastructureItem("Changing room", R.drawable.ic_dollar)
                        InfrastructureItem("Indoor", R.drawable.ic_dollar)
                        InfrastructureItem("9:00 - 23:00", R.drawable.ic_dollar)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${details.pricePerHour}sum/hour",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick = { /* Handle order */ },
                            modifier = Modifier.width(120.dp)
                        ) {
                            Text("Order")
                        }
                    }
                }
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun InfrastructureItem(text: String, iconRes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.size(24.dp)
        )
        Text(text = text, fontSize = 12.sp)
    }
}