package com.bron24.bron24_android.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.bron24.bron24_android.core.domain.model.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color.White)
            .padding(vertical = 24.dp, horizontal = 52.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavigationItem(
            icon = Icons.Filled.Home,
            label = "Home",
            selected = currentRoute == Screen.HomePage.route,
            onClick = { navController.navigate(Screen.HomePage.route) }
        )
        BottomNavigationItem(
            icon = Icons.Filled.Email,
            label = "Map",
            selected = currentRoute == Screen.MapPage.route,
            onClick = { navController.navigate(Screen.MapPage.route) }
        )
        BottomNavigationItem(
            icon = Icons.Filled.ShoppingCart,
            label = "Cart",
            selected = currentRoute == Screen.CartPage.route,
            onClick = { navController.navigate(Screen.CartPage.route) }
        )
        BottomNavigationItem(
            icon = Icons.Filled.Person,
            label = "Profile",
            selected = currentRoute == Screen.ProfilePage.route,
            onClick = { navController.navigate(Screen.ProfilePage.route) }
        )
    }
}

@Composable
fun BottomNavigationItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = 24.dp,
                    color = if (selected) Color(0xFF3DDA7E) else Color.Gray
                ),
                onClick = onClick
            )
            .background(
                if (selected) Color(0xFF3DDA7E).copy(alpha = 0.12f) else Color.Transparent,
                shape = CircleShape
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) Color(0xFF3DDA7E) else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                color = if (selected) Color(0xFF3DDA7E) else Color.Gray
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()

    BottomNavigationBar(navController = navController)
}