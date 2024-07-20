package com.bron24.bron24_android.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.bron24.bron24_android.R
import com.bron24.bron24_android.core.domain.model.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val gilroyFontFamily = FontFamily(
    Font(resId = R.font.gilroy_regular, weight = FontWeight.Normal),
    Font(resId = R.font.gilroy_bold, weight = FontWeight.Bold)
)

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
        BottomNavigationItem(
            icon = R.drawable.ic_home,
            label = "Home",
            selected = currentRoute == Screen.HomePage.route,
            onClick = { navController.navigate(Screen.HomePage.route) }
        )
        BottomNavigationItem(
            icon = R.drawable.ic_map,
            label = "Nearby",
            selected = currentRoute == Screen.MapPage.route,
            onClick = { navController.navigate(Screen.MapPage.route) }
        )
        BottomNavigationItem(
            icon = R.drawable.ic_wallet,
            label = "Orders",
            selected = currentRoute == Screen.CartPage.route,
            onClick = { navController.navigate(Screen.CartPage.route) }
        )
        BottomNavigationItem(
            icon = R.drawable.ic_person,
            label = "Profile",
            selected = currentRoute == Screen.ProfilePage.route,
            onClick = { navController.navigate(Screen.ProfilePage.route) }
        )
    }
}

@Composable
fun BottomNavigationItem(
    icon: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val rippleRadius = 500.dp

    Box(
        modifier = Modifier
            .padding(10.dp)
            .size(56.dp)  // Ensure the size is fixed to prevent squeezing
            .clip(CircleShape)
            .background(if (selected) Color(0xFF3DDA7E).copy(alpha = 0.12f) else Color.Transparent)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = rippleRadius,
                    color = if (selected) Color(0xFF3DDA7E) else Color.Gray
                ),
                onClick = {
                    onClick()
                    LaunchedEffect(interactionSource) {
                        val press = PressInteraction.Press(Offset.Zero)
                        interactionSource.emit(press)
                        delay(500)
                        interactionSource.emit(PressInteraction.Release(press))
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = if (selected) Color(0xFF3DDA7E) else Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = if (selected) Color(0xFF3DDA7E) else Color.Gray,
                    fontSize = 10.sp,
                    lineHeight = 12.25.sp,
                    shadow = if (selected) Shadow(
                        color = Color(0xFF3DDA7E),
                        offset = Offset(0f, 0.5f),
                        blurRadius = 6f
                    ) else null
                )
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
