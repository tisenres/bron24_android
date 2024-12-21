package com.bron24.bron24_android.screens.menu_pages.profile_page

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.CustomAppBar
import com.bron24.bron24_android.components.items.ItemEditProfile
import com.bron24.bron24_android.components.items.ItemProfileTask
import com.bron24.bron24_android.screens.main.theme.Black
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState

object ProfilePage : Tab {
    private fun readResolve(): Any = ProfilePage
    override val options: TabOptions
        @Composable get() {
            val icon =
                rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_person))
            return TabOptions(
                index = 5u,
                title = "Profile",
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        val viewModel: ProfilePageContract.ViewModel = getViewModel<ProfilePageVM>()
        val state  = viewModel.collectAsState()
        ProfilePageContent(state = state, intent = viewModel::onDispatchers)
    }

}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun ProfilePageContent(
    state: State<ProfilePageContract.UISate>,
    intent:(ProfilePageContract.Intent)->Unit
) {
//    viewModel.initData()
//    val context = LocalContext.current
//    var state = viewModel.collectAsState()
//    viewModel.collectSideEffect {
//        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        CustomAppBar(title = "Profile", actions = {
            ItemEditProfile {
                //click
            }
        })

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
                                ), shape = CircleShape, width = 5.dp
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
                        text = "",
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
            ItemProfileTask(text = "",
                startIcons = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = "icon",
                        modifier = Modifier.size(20.dp)
                    )
                }) {}
            ItemProfileTask(text = "",
                startIcons = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_phone),
                        contentDescription = "icon",
                        modifier = Modifier.size(20.dp)
                    )
                }) {}
            ItemProfileTask(text = "Change Language", startIcons = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_language),
                    contentDescription = "icon",
                    modifier = Modifier.size(20.dp)
                )
            }) {}
            ItemProfileTask(text = "Favorites", startIcons = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_favorite),
                    contentDescription = "icon",
                    modifier = Modifier.size(20.dp)
                )
            }) {}
            Text(
                text = "Support",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gilroyFontFamily,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp)
            )

            ItemProfileTask(text = "Help", startIcons = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_help),
                    contentDescription = "icon",
                    modifier = Modifier.size(20.dp)
                )
            }) {}
            ItemProfileTask(text = "About Us", startIcons = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = "icon",
                    modifier = Modifier.size(22.dp)
                )
            }) {}
            ItemProfileTask(text = "Add Venue", startIcons = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "icon",
                    modifier = Modifier.size(20.dp)
                )
            }) {}
            Row(modifier = Modifier
                .padding(vertical = 32.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable(
                    interactionSource = MutableInteractionSource(), indication = null
                ) { }
                .border(width = 2.dp, color = Color.Red, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 40.dp, vertical = 10.dp)

                .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically) {
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

@Preview(showBackground = true)
@Composable
private fun ProPreview() {
    ProfilePage
}