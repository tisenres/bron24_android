package com.bron24.bron24_android.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(70.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomBottomBarItem(
            iconRes = R.drawable.ic_home,
            label = "Home",
            isSelected = currentRoute == Screen.HomePage.route,
            onClick = { navController.navigate(Screen.HomePage.route) }
        )
        CustomBottomBarItem(
            iconRes = R.drawable.ic_map,
            label = "Nearby",
            isSelected = currentRoute == Screen.MapPage.route,
            onClick = { navController.navigate(Screen.MapPage.route) }
        )
        CustomBottomBarItem(
            iconRes = R.drawable.ic_wallet,
            label = "Orders",
            isSelected = currentRoute == Screen.CartPage.route,
            onClick = { navController.navigate(Screen.CartPage.route) }
        )
        CustomBottomBarItem(
            iconRes = R.drawable.ic_person,
            label = "Profile",
            isSelected = currentRoute == Screen.ProfilePage.route,
            onClick = { navController.navigate(Screen.ProfilePage.route) }
        )
    }
}

@Composable
fun CustomBottomBarItem(
    iconRes: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val selectedColor = Color(0xFF3DDA7E)
    val unselectedColor = Color(0xFF888888)

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = if (isSelected) selectedColor else unselectedColor,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            lineHeight = 12.25.sp,
            color = if (isSelected) selectedColor else unselectedColor,
            modifier = Modifier.padding(top = 5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(navController = navController)
}