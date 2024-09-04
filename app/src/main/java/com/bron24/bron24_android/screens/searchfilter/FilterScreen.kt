package com.bron24.bron24_android.screens.searchfilter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun FilterScreen(
    onApplyFilter: (FilterOptions) -> Unit,
    onDismiss: () -> Unit
) {
    var availableTime by remember { mutableStateOf("") }
    var minPrice by remember { mutableStateOf(0) }
    var maxPrice by remember { mutableStateOf(100000) }
    var infrastructure by remember { mutableStateOf(false) }
    var district by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Filter",
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color(0xFF32B768)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Available Time
        Text("Available", fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilterChip(
                selected = availableTime == "all_time",
                onClick = { availableTime = "all_time" },
                label = { Text("All time") }
            )
            FilterChip(
                selected = availableTime == "morning",
                onClick = { availableTime = "morning" },
                label = { Text("Morning") }
            )
            FilterChip(
                selected = availableTime == "afternoon",
                onClick = { availableTime = "afternoon" },
                label = { Text("After noon") }
            )
            FilterChip(
                selected = availableTime == "evening",
                onClick = { availableTime = "evening" },
                label = { Text("Evening") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Price Range
        Text("Price Range", fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = minPrice.toString(),
                onValueChange = { minPrice = it.toIntOrNull() ?: 0 },
                label = { Text("Min Price") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = maxPrice.toString(),
                onValueChange = { maxPrice = it.toIntOrNull() ?: 100000 },
                label = { Text("Max Price") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Infrastructure
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = infrastructure,
                onCheckedChange = { infrastructure = it }
            )
            Text("Infrastructure", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // District
        OutlinedTextField(
            value = district,
            onValueChange = { district = it },
            label = { Text("District") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Apply Button
        Button(
            onClick = {
                onApplyFilter(
                    FilterOptions(
                        availableTime = availableTime,
                        minPrice = minPrice,
                        maxPrice = maxPrice,
                        infrastructure = infrastructure,
                        district = district
                    )
                )
                onDismiss()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Apply Filter")
        }
    }
}

data class FilterOptions(
    val availableTime: String,
    val minPrice: Int,
    val maxPrice: Int,
    val infrastructure: Boolean,
    val district: String
)