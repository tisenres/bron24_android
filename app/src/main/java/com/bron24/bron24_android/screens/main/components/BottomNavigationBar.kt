package com.bron24.bron24_android.screens.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.Screen
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color.White)
            .padding(start = 30.dp, end = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomBottomBarItem(
            iconRes = R.drawable.ic_home,
            label = stringResource(id = R.string.home),
            isSelected = currentRoute == Screen.HomePage.route,
            onClick = { navController.navigate(Screen.HomePage.route) }
        )
        CustomBottomBarItem(
            iconRes = R.drawable.ic_map,
            label = stringResource(id = R.string.nearby),
            isSelected = currentRoute == Screen.MapPage.route,
            onClick = { navController.navigate(Screen.MapPage.route) }
        )
        CustomBottomBarItem(
            iconRes = R.drawable.ic_wallet,
            label = stringResource(id = R.string.orders),
            isSelected = currentRoute == Screen.CartPage.route,
            onClick = { navController.navigate(Screen.CartPage.route) }
        )
        CustomBottomBarItem(
            iconRes = R.drawable.ic_person,
            label = stringResource(id = R.string.profile),
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
    val unselectedColor = Color(0xFFC6C6C6)

    var currentColor by remember { mutableStateOf(unselectedColor) }

    LaunchedEffect(isSelected) {
        currentColor = if (isSelected) selectedColor else unselectedColor
    }

    Column(
        modifier = Modifier
            .clickable(
                onClick = {
                    onClick()
                    currentColor = selectedColor
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically),
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

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(navController = navController)
}