package com.bron24.bron24_android.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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

val gilroyFontFamily = FontFamily(
    Font(resId = R.font.gilroy_regular, weight = FontWeight.Normal),
    Font(resId = R.font.gilroy_bold, weight = FontWeight.Bold)
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White),
        contentColor = Color(0xFF26A045)
    ) {
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.ic_home), contentDescription = "Home") },
            label = { Text("Home", fontFamily = gilroyFontFamily, fontWeight = FontWeight.Bold, fontSize = 10.sp) },
            selected = currentRoute == Screen.HomePage.route,
            onClick = { navController.navigate(Screen.HomePage.route) },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF26A045),
                unselectedIconColor = Color(0xFF888888),
                selectedTextColor = Color(0xFF26A045),
                unselectedTextColor = Color(0xFF888888),
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.ic_map), contentDescription = "Nearby") },
            label = { Text("Nearby", fontFamily = gilroyFontFamily, fontWeight = FontWeight.Bold, fontSize = 10.sp) },
            selected = currentRoute == Screen.MapPage.route,
            onClick = { navController.navigate(Screen.MapPage.route) },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF26A045),
                unselectedIconColor = Color(0xFF888888),
                selectedTextColor = Color(0xFF26A045),
                unselectedTextColor = Color(0xFF888888),
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.ic_wallet), contentDescription = "Orders") },
            label = { Text("Orders", fontFamily = gilroyFontFamily, fontWeight = FontWeight.Bold, fontSize = 10.sp) },
            selected = currentRoute == Screen.CartPage.route,
            onClick = { navController.navigate(Screen.CartPage.route) },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF26A045),
                unselectedIconColor = Color(0xFF888888),
                selectedTextColor = Color(0xFF26A045),
                unselectedTextColor = Color(0xFF888888),
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.ic_person), contentDescription = "Profile") },
            label = { Text("Profile", fontFamily = gilroyFontFamily, fontWeight = FontWeight.Bold, fontSize = 10.sp) },
            selected = currentRoute == Screen.ProfilePage.route,
            onClick = { navController.navigate(Screen.ProfilePage.route) },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF26A045),
                unselectedIconColor = Color(0xFF888888),
                selectedTextColor = Color(0xFF26A045),
                unselectedTextColor = Color(0xFF888888),
                indicatorColor =Color.Transparent
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