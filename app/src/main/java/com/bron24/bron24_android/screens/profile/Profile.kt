package com.bron24.bron24_android.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun Profile(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        // Scrollable Profile Content

        val scrollState = rememberLazyListState()
        val context = LocalContext.current
        val toolbarHeight = 48.dp

        val toolbarVisible by remember {
            derivedStateOf {
                scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 0
            }
        }

        AnimatedToolbar(
            visible = toolbarVisible,
            title = "User Profile",
            onBackClick = onBackClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(toolbarHeight)
                .zIndex(1f)
        )

//        Box(
//            modifier = Modifier
//                .clip(RoundedCornerShape(50.dp))
//                .fillMaxWidth()
//                .weight(1f)
//                .background(Color.White, shape = RoundedCornerShape(50.dp))
//                .padding(top = 16.dp)
//                .verticalScroll(rememberScrollState())
//        ) {
//            ProfileContentTop()
//
//            ProfileInfoSection()
//
//            ProfileAccountActions()
//
//        }
//
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

            ProfileAccountActions()

        }
    }
}

@Composable
fun ProfileContentTop() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF32B768))
            .padding(bottom = 99.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://i.imgur.com/1tMFzp8.png")
                .placeholder(R.drawable.placeholder)
                .crossfade(true)
                .build(),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(horizontal = 122.dp)
                .size(150.dp)
        )
        Spacer(modifier = Modifier.height(13.dp))
        Text(
            text = "Cristiano Ronaldo",
            style = TextStyle(
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = Color.White,
                lineHeight = 20.sp,
            ),
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_edit_24),
                contentDescription = "Edit",
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Edit Profile",
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White,
                    lineHeight = 20.sp,
                ),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedToolbar(
    visible: Boolean,
    title: String?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.Text(
                        text = title ?: "Unknown field",
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF3C2E56),
                            lineHeight = 22.sp,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color(0xFF3C2E56),
                navigationIconContentColor = Color(0xFF3C2E56)
            )
        )
    }
}

@Composable
fun ProfileInfoSection() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 28.dp, vertical = 24.dp)
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
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color(0xFF636363),
                lineHeight = 20.sp,
                textDecoration = TextDecoration.Underline
            ),
        )
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 19.dp)
            .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 18.dp)
    ) {
        ProfileInfoItem(label = "Full name", value = "Cristiano Ronaldo")
        Spacer(modifier = Modifier.height(10.dp))
        ProfileInfoItem(label = "Phone number", value = "+998 97 777 07 07")
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
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp,
            )
        )
    }
}

@Composable
fun ProfileAccountActions() {
    Column(
        modifier = Modifier
            .padding(horizontal = 19.dp, vertical = 14.dp)
            .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 24.dp)
    ) {
        ProfileActionButton(text = "Change password")
        Spacer(modifier = Modifier.height(10.dp))
        ProfileActionButton(text = "Logout")
        Spacer(modifier = Modifier.height(10.dp))
        ProfileActionButton(text = "Delete account")
    }
}

@Composable
fun ProfileActionButton(text: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
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

@Preview(widthDp = 390, heightDp = 794)
@Composable
fun ProfilePreview() {
    Profile({})
}
