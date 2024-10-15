package com.bron24.bron24_android.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R

@Composable
fun Profile(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(50.dp))
            .background(Color.White)
    ) {
        // Profile Info Section
        ProfileHeader()

        // About and Profile Edit
        ProfileContent()

        // Delete Account Button
        DeleteAccountButton()

        // Bottom Navigation Bar
        BottomNavigationBar()

        // Status Bar
        StatusBarIPhoneXOrNewer()
    }
}

@Composable
fun ProfileHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .background(Color(0xff32b768))
    ) {
        // Profile Picture and Name
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_empty_user),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(150.dp)
                    .border(BorderStroke(5.dp, Color.White), RoundedCornerShape(50))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Cristiano Ronaldo",
                color = Color.White,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            EditProfileButton()
        }
    }
}

@Composable
fun EditProfileButton() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .padding(top = 4.dp)
//            .align(Alignment.CenterHorizontally)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_empty_user),
            contentDescription = "Edit Icon",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = "Edit Profile",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProfileContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "About",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Edit Profile",
                color = Color(0xff636363),
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProfileInfoItem("Full name", "Cristiano Ronaldo")
        ProfileInfoItem("Phone number", "+99897 777 07 07")
        ProfileActionButton("Change password")
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = label, fontSize = 14.sp)
        Text(text = value, fontSize = 14.sp, color = Color.Black)
    }
}

@Composable
fun ProfileActionButton(text: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Handle click */ }
    ) {
        Text(text = text, fontSize = 14.sp, color = Color.Black)
        Image(
            painter = painterResource(id = R.drawable.ic_empty_user),
            contentDescription = "Arrow",
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
fun DeleteAccountButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 28.dp)
            .clip(RoundedCornerShape(5.dp))
            .border(BorderStroke(1.dp, Color(0xffd9d9d9)))
    ) {
        Text(
            text = "Delete account",
            color = Color.Black,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun BottomNavigationBar() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color.White)
            .padding(horizontal = 27.dp, vertical = 16.dp)
    ) {
        BottomNavItem(R.drawable.ic_empty_user, "home-3-fill", Color(0xffc6c6c6))
        BottomNavItem(R.drawable.ic_empty_user, "Address", Color(0xffc6c6c6))
        BottomNavItem(R.drawable.ic_empty_user, "Wallet", Color(0xffc6c6c6))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BottomNavItem(R.drawable.ic_empty_user, "user-fill", Color(0xff3dda7e))
            Text(
                text = "Orders",
                color = Color(0xff3dda7e),
                fontSize = 10.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BottomNavItem(resourceId: Int, description: String, tintColor: Color) {
    Image(
        painter = painterResource(id = resourceId),
        contentDescription = description,
        colorFilter = ColorFilter.tint(tintColor),
        modifier = Modifier.size(24.dp)
    )
}

@Composable
fun StatusBarIPhoneXOrNewer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.uzcard),
            contentDescription = "Right Side",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(67.dp)
        )
        DarkModeFalseTypeDefault()
    }
}

@Composable
fun DarkModeFalseTypeDefault() {
    Box(
        modifier = Modifier
            .width(54.dp)
            .height(21.dp)
            .clip(RoundedCornerShape(24.dp))
    ) {
        Text(
            text = "9:41",
            color = Color(0xff181818),
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(widthDp = 390, heightDp = 794)
@Composable
fun ProfilePreview() {
    Profile()
}
