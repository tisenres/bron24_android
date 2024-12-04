package com.bron24.bron24_android.screens.profile

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.helper.util.formatPhoneNumber
import com.bron24.bron24_android.helper.util.presentation.components.items.CustomAppBar
import com.bron24.bron24_android.helper.util.presentation.components.items.ItemEditProfile
import com.bron24.bron24_android.helper.util.presentation.components.items.ItemProfileTask
import com.bron24.bron24_android.screens.main.theme.Black
import com.bron24.bron24_android.screens.main.theme.Black17
import com.bron24.bron24_android.screens.main.theme.Success
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.skydoves.orbital.Orbital
import com.skydoves.orbital.animateBounds
import com.skydoves.orbital.rememberMovableContentOf
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    viewModel.initData()
    val context = LocalContext.current
    var state = viewModel.collectAsState()
    viewModel.collectSideEffect {
        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        CustomAppBar(
            title = "Profile",
            startIcons = {
                IconButton(
                    onClick = {

                    },
                    Modifier
                        .size(28.dp)
                        .clip(CircleShape),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "icons",
                        tint = Black17,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            actions = {
                ItemEditProfile {

                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "icon",
                    modifier = Modifier.align(Alignment.BottomStart),
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .size(110.dp)
                            .clip(shape = CircleShape)
                            .border(
                                brush = Brush.linearGradient(
                                    start = Offset(0f, 0f),
                                    colors = listOf(White, Color(0xff2D6544)),
                                    end = Offset(0f, Float.POSITIVE_INFINITY)
                                ),
                                shape = CircleShape,
                                width = 5.dp
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ball_pic),
                            contentDescription = "img",
                            modifier = Modifier
                                .size(110.dp)
                                .align(Alignment.Center),
                            contentScale = ContentScale.Crop

                        )
                    }
                    Text(
                        text = "${state.value.user?.firstName} ${state.value.user?.lastName}",
                        modifier = Modifier.padding(bottom = 60.dp),
                        color = Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Text(
                text = "Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gilroyFontFamily,
                modifier = Modifier.padding(horizontal = 20.dp)

            )
            ItemProfileTask(
                text = "${state.value.user?.firstName} ${state.value.user?.lastName}",
                startIcons = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = "icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
            ItemProfileTask(
                text = state.value.user?.phoneNumber?.formatPhoneNumber()?:"",
                startIcons = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_phone),
                        contentDescription = "icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
            ItemProfileTask(
                text = "Change Language",
                startIcons = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_language),
                        contentDescription = "icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
            ItemProfileTask(
                text = "Favorites",
                startIcons = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = "icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
            Text(
                text = "Support",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gilroyFontFamily,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp)
            )

            ItemProfileTask(
                text = "Help",
                startIcons = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_help),
                        contentDescription = "icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
            ItemProfileTask(
                text = "About Us",
                startIcons = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_info),
                        contentDescription = "icon",
                        modifier = Modifier.size(22.dp)
                    )
                }
            )
            ItemProfileTask(
                text = "Add Venue",
                startIcons = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
//            Spacer(modifier = Modifier
//                .height(40.dp)
//                .background(color = Color.White))
            Row(modifier = Modifier
                .padding(vertical = 32.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { }
                .border(width = 2.dp, color = Color.Red, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 40.dp, vertical = 10.dp)

                .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = "icon",
                    tint = Color.Red
                )
                Text(
                    text = "Logout",
                    fontSize = 14.sp,
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White, darkIcons = true)
}
//@Preview(showBackground = true)
//@Composable
//fun ProPreview() {
//    ProfilePage()
//}