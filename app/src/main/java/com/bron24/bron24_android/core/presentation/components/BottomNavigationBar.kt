package com.bron24.bron24_android.core.presentation.components

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bron24.bron24_android.core.domain.model.Screen
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.times
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.HomePage.route

    val items = listOf(Screen.HomePage, Screen.MapPage, Screen.CartPage, Screen.ProfilePage)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth / items.size
    val selectedItemIndex by remember { derivedStateOf { items.indexOfFirst { it.route == currentRoute } } }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 27.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, screen ->
                BottomNavigationItem(
                    icon = {
                        when (screen.route) {
                            Screen.HomePage.route -> Icon(
                                Icons.Filled.Home,
                                contentDescription = null,
                                tint = if (currentRoute == screen.route) Color(0xFF3DDA7E) else Color.Gray
                            )
                            Screen.MapPage.route -> Icon(
                                Icons.Filled.Email,
                                contentDescription = null,
                                tint = if (currentRoute == screen.route) Color(0xFF3DDA7E) else Color.Gray
                            )
                            Screen.CartPage.route -> Icon(
                                Icons.Filled.ShoppingCart,
                                contentDescription = null,
                                tint = if (currentRoute == screen.route) Color(0xFF3DDA7E) else Color.Gray
                            )
                            Screen.ProfilePage.route -> Icon(
                                Icons.Filled.Person,
                                contentDescription = null,
                                tint = if (currentRoute == screen.route) Color(0xFF3DDA7E) else Color.Gray
                            )
                        }
                    },
                    label = {
                        Text(
                            text = when (screen.route) {
                                Screen.HomePage.route -> "Home"
                                Screen.MapPage.route -> "Map"
                                Screen.CartPage.route -> "Cart"
                                Screen.ProfilePage.route -> "Profile"
                                else -> ""
                            },
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF3DDA7E)
                            ),
                            modifier = Modifier.alpha(if (currentRoute == screen.route) 1f else 0f)
                        )
                    },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route)
                    }
                )
            }
        }

        val indicatorOffset by animateDpAsState(
            targetValue = selectedItemIndex * itemWidth,
            animationSpec = TweenSpec(durationMillis = 300)
        )

        Image(
            painter = painterResource(id = R.drawable.ellipse_green),
            contentDescription = "Indicator",
            modifier = Modifier
                .offset(x = indicatorOffset + itemWidth / 2 - 10.dp) // Adjust the position
                .align(Alignment.BottomStart)
                .size(20.dp)
        )
    }
}

@Composable
fun BottomNavigationItem(
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()
        label()
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewBottomNavigationBar() {
    val navController = rememberNavController()
    BottomNavigationBar(navController = navController)
}
