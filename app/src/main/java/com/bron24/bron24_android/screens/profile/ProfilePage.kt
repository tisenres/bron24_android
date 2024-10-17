package com.bron24.bron24_android.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastManager
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastType
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val profileState by viewModel.profileState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPersonalUserData()
    }

    when (val state = profileState) {
        is ProfileState.Loading -> {
            // Show a loading indicator
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ProfileState.Success -> {
            val user = state.user
            // Proceed to display the UI with user data
            ProfileContent(user, modifier)
        }
        is ProfileState.Initial -> {
            // Optionally, show an initial state or nothing
        }

        is ProfileState.Error -> {
            ToastManager.showToast(type = ToastType.ERROR, message = "Unable to load user data")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(user: User, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White)
        ) {

//            TopAppBar(
//                modifier = Modifier.height(60.dp), // Adjust the height as needed
//                title = {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .padding(horizontal = 24.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "Profile",
//                            style = TextStyle(
//                                fontFamily = gilroyFontFamily,
//                                fontWeight = FontWeight.ExtraBold,
//                                fontSize = 28.sp,
//                                color = Color.Black,
//                                lineHeight = 30.sp,
//                            ),
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.White,
//                    titleContentColor = Color.Black,
//                )
//            )



            Column(
                modifier = Modifier
//                .clip(RoundedCornerShape(50.dp))
                    .fillMaxWidth()
//                .weight(1f)
//                .background(Color.White, shape = RoundedCornerShape(50.dp))
                    .padding(top = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Text(
                    text = "Profile",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                        color = Color.Black,
                        lineHeight = 30.sp,
                    ),
                    modifier = Modifier.padding(horizontal = 25.dp, vertical = 12.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                ProfileContentTop(user)

                ProfileInfoSection(user)

                Spacer(modifier = Modifier.height(14.dp))

                ProfileAccountAction(title = "Change password", {})
                Spacer(modifier = Modifier.height(14.dp))
                ProfileAccountAction(title = "Log out", {})
                Spacer(modifier = Modifier.height(14.dp))
                ProfileAccountAction(title = "Delete account", {})
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun ProfileContentTop(user: User) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // Adjusted the offset values to move the stripe lower and partially off-screen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp) // Adjust height as needed
                .align(Alignment.TopCenter)
                .graphicsLayer {
                    rotationZ = -13.45f // Rotate the stripe
                    // Translate to position the stripe lower
                    translationY = 65.dp.toPx() // Move it down by 150dp
                    // Expand beyond screen borders horizontally
                    scaleX = 2f // Double the width
                }
                .background(Color(0xFF32B768))
        )

        // Column for profile details (image, name, edit profile)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 99.dp) // Adjust padding based on your needs
        ) {
            // Profile Image (large, centered)
            Image(
                painter = rememberAsyncImagePainter(model = R.drawable.ball_pic),
                contentDescription = "profile_image",
                modifier = Modifier
                    .size(150.dp) // Large image size
                    .clip(CircleShape)
                    .border(5.dp, Color.White, CircleShape), // Optional white border
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(13.dp))

            // Profile Name (Cristiano Ronaldo)
            Text(
                text = user.firstName + " " + user.lastName,
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp, // Larger text for better visibility
                    color = Color.White,
                    lineHeight = 30.sp,
                )
            )

//            Spacer(modifier = Modifier.height(8.dp))

            // Edit Profile Row with Icon
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.edit_icon),
//                    contentDescription = "Edit",
//                    modifier = Modifier.size(16.dp)
//                )
//                Spacer(modifier = Modifier.width(6.dp))
//                Text(
//                    text = "Edit Profile",
//                    style = TextStyle(
//                        fontFamily = gilroyFontFamily,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 16.sp, // Edit profile text size
//                        color = Color.White,
//                        lineHeight = 20.sp
//                    )
//                )
//            }
        }
    }
}

@Composable
fun ProfileInfoSection(user: User) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 24.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "About",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                lineHeight = 20.sp,
            ),
        )

        Text(
            text = "Edit Profile",
            fontSize = 16.sp,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFF636363),
                lineHeight = 20.sp,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.clickable(
                onClick = { /* Handle Edit Profile click */ },
                indication = ripple(),
                interactionSource = remember { MutableInteractionSource() }
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 18.dp)
        ) {
            ProfileInfoItem(label = "Full name", value = user.firstName + " " + user.lastName)
            Spacer(modifier = Modifier.height(10.dp))
            ProfileInfoItem(label = "Phone number", value = user.phoneNumber)
        }
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp,
            ),
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp,
            )
        )
    }
}

@Composable
fun ProfileAccountAction(title: String, onActionClick: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 15.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Black,
                    lineHeight = 20.sp,
                ),
            )
        }
    }
}

@Preview(widthDp = 390, heightDp = 794)
@Composable
fun ProfilePreview() {
    ProfilePage()
}
