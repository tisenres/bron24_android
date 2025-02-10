package com.bron24.bron24_android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
//        val items = listOf(
//            Triple(R.drawable.ic_home, stringResource(id = R.string.home), Screen.HomePage.route),
//            Triple(R.drawable.ic_map, stringResource(id = R.string.nearby), Screen.MapPage.route),
//            Triple(R.drawable.ic_person, stringResource(id = R.string.profile), Screen.ProfilePage.route)
//        )

//        items.forEach { (iconRes, label, route) ->
//            CustomBottomBarItem(
//                iconRes = iconRes,
//                label = label,
//                isSelected = currentRoute == route,
//                onClick = { navController.navigate(route) }
//            )
//        }
    }
}

@Composable
fun CustomBottomBarItem(
    iconRes: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val selectedColor = MaterialTheme.colorScheme.tertiary
    val unselectedColor = Color(0xFFC6C6C6)

    var currentColor by remember { mutableStateOf(unselectedColor) }

    LaunchedEffect(isSelected) {
        currentColor = if (isSelected) selectedColor else unselectedColor
    }

    Box(
        modifier = Modifier
            .size(72.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        // Ripple effect container
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = {
                        onClick()
                        currentColor = selectedColor
                    },
                    indication = ripple(
                        bounded = false,
                        color = Color.Gray,
                        radius = 35.dp // Increased radius for "infinity" effect
                    ),
                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                )
        )

        // Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                tint = currentColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = label,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                lineHeight = 12.25.sp,
                color = currentColor,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(navController = navController)
}