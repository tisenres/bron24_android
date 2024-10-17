package com.bron24.bron24_android.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        val scrollState = rememberLazyListState()
        val context = LocalContext.current
        val toolbarHeight = 40.dp

        val toolbarVisible by remember {
            derivedStateOf {
                scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 0
            }
        }

        TopAppBar(
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Profile",
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 28.sp,
                            color = Color.Black,
                            lineHeight = 30.sp,
                        ),
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black,
            )
        )

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White, shape = RoundedCornerShape(50.dp))
                .padding(top = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ProfileContentTop()

            ProfileInfoSection()

            Spacer(modifier = Modifier.height(14.dp))


            ProfileAccountAction(title = "Change password", {})
            Spacer(modifier = Modifier.height(14.dp))
            ProfileAccountAction(title = "Logout", {})
            Spacer(modifier = Modifier.height(14.dp))
            ProfileAccountAction(title = "Delete account", {})

        }
    }
}

@Composable
fun ProfileContentTop() {
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
                    translationY = 140.dp.toPx() // Move it down by 150dp
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
                .padding(top = 80.dp, bottom = 99.dp) // Adjust padding based on your needs
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
                text = "Cristiano Ronaldo",
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp, // Larger text for better visibility
                    color = Color.White,
                    lineHeight = 30.sp,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Edit Profile Row with Icon
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.edit_icon),
                    contentDescription = "Edit",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Edit Profile",
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp, // Edit profile text size
                        color = Color.White,
                        lineHeight = 20.sp
                    )
                )
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun AnimatedToolbar(
//    visible: Boolean,
//    title: String?,
//    onBackClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    AnimatedVisibility(
//        visible = visible,
//        enter = fadeIn(),
//        exit = fadeOut(),
//        modifier = modifier
//    ) {
//        TopAppBar(
//            title = {
//                Box(
//                    modifier = Modifier.fillMaxHeight(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    androidx.compose.material3.Text(
//                        text = title ?: "Unknown field",
//                        style = TextStyle(
//                            fontFamily = gilroyFontFamily,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 18.sp,
//                            color = Color(0xFF3C2E56),
//                            lineHeight = 22.sp,
//                        ),
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis,
//                    )
//                }
//            },
//            navigationIcon = {
//                IconButton(onClick = onBackClick) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowBack,
//                        contentDescription = "Back"
//                    )
//                }
//            },
//            colors = TopAppBarDefaults.topAppBarColors(
//                containerColor = Color.White,
//                titleContentColor = Color(0xFF3C2E56),
//                navigationIconContentColor = Color(0xFF3C2E56)
//            )
//        )
//    }
//}

@Composable
fun ProfileInfoSection() {
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
            ProfileInfoItem(label = "Full name", value = "Cristiano Ronaldo")
            Spacer(modifier = Modifier.height(10.dp))
            ProfileInfoItem(label = "Phone number", value = "+998 97 777 07 07")
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
    Profile()
}
