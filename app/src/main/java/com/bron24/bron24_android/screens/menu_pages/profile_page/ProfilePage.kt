package com.bron24.bron24_android.screens.menu_pages.profile_page

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.CustomAppBar
import com.bron24.bron24_android.components.items.CustomDialog
import com.bron24.bron24_android.components.items.ItemEditProfile
import com.bron24.bron24_android.components.items.ItemProfileTask
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.helper.util.formatPhoneNumber
import com.bron24.bron24_android.screens.main.theme.Black
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import openDialer
import org.orbitmvi.orbit.compose.collectAsState

object ProfilePage : Tab {
    private fun readResolve(): Any = ProfilePage
    override val options: TabOptions
        @Composable get() {
            val icon =
                rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_person))
            return TabOptions(
                index = 5u, title = stringResource(id = R.string.profile), icon = icon
            )
        }

    @Composable
    override fun Content() {
        val viewModel: ProfilePageContract.ViewModel = getViewModel<ProfilePageVM>()
        val state = viewModel.collectAsState()
        ProfilePageContent(state = state, intent = viewModel::onDispatchers)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun ProfilePageContent(
    state: State<ProfilePageContract.UIState>,
    intent: (ProfilePageContract.Intent) -> Unit
) {
    var isClickable by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val user: User = state.value.user
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
    LaunchedEffect(openBottomSheet) {
        if (openBottomSheet) {
            coroutineScope.launch {
                bottomSheetState.show()
            }
        } else {
            coroutineScope.launch {
                bottomSheetState.hide()
            }
        }
    }
    val tab = LocalTabNavigator.current
    BackHandler {
        tab.current = HomePage
    }
    LaunchedEffect(isClickable) {
        delay(300)
        isClickable = true
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        CustomAppBar(title = stringResource(id = R.string.profile), actions = {
            ItemEditProfile {
                if (isClickable) {
                    intent(ProfilePageContract.Intent.OpenEdit)
                    isClickable = false
                }
            }
        }) {
            
        }

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
                    modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth(),
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
                        text = user.firstName,
                        modifier = Modifier.padding(bottom = 60.dp),
                        color = Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Text(
                text = stringResource(id = R.string.profile),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gilroyFontFamily,
                modifier = Modifier.padding(horizontal = 20.dp)

            )
            ItemProfileTask(text = "${user.firstName} ${user.lastName}", startIcons = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "icon",
                    modifier = Modifier.size(20.dp)
                )
            }) {}
            ItemProfileTask(text = user.phoneNumber, startIcons = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone),
                    contentDescription = "icon",
                    modifier = Modifier.size(20.dp)
                )
            }) {}
            ItemProfileTask(text = stringResource(id = R.string.change_lan), startIcons = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_language),
                    contentDescription = "icon",
                    modifier = Modifier.size(20.dp)
                )
            }) {
                if (isClickable) {
                    intent(ProfilePageContract.Intent.OpenChangeLanguage)
                    isClickable = false
                }
            }
            ItemProfileTask(text = stringResource(id = R.string.favorites), startIcons = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_favorite),
                    contentDescription = "icon",
                    modifier = Modifier.size(20.dp)
                )
            }) {
                if (isClickable) {
                    ToastManager.showToast("Qilinmoqda",ToastType.INFO)
                    isClickable = false
                }
            }
            Text(
                text = stringResource(id = R.string.support),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gilroyFontFamily,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp)
            )

            ItemProfileTask(text = stringResource(id = R.string.help), startIcons = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_help),
                    contentDescription = "icon",
                    modifier = Modifier.size(20.dp)
                )
            }) {
                openBottomSheet = true
            }
            ItemProfileTask(text = stringResource(id = R.string.about_us), startIcons = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = "icon",
                    modifier = Modifier.size(22.dp)
                )
            }) {
                if (isClickable) {
                    intent(ProfilePageContract.Intent.OpenAboutUs)
                    isClickable = false
                }
            }
            ItemProfileTask(text = stringResource(id = R.string.add_venue), startIcons = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "icon",
                    modifier = Modifier.size(20.dp)
                )
            }) {
                if (isClickable) {
                    intent(ProfilePageContract.Intent.OpenAddVenue)
                    isClickable = false
                }
            }
            Row(modifier = Modifier
                .padding(vertical = 36.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable(
                    interactionSource = MutableInteractionSource(), indication = null
                ) {
                    showDialog = true
                }
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
                    text = stringResource(id = R.string.logout),
                    fontSize = 14.sp,
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
    val context = LocalContext.current
    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
            containerColor = White,
            tonalElevation = 100.dp,

            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.contact_us),
                    textAlign = TextAlign.Center,
                    color = Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy_bold)),
                    modifier = Modifier.padding(top = 2.dp, bottom = 10.dp),
                )

                ItemProfileTask(
                    text = "Telegram",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    startIcons = {
                        Icon(
                            painter = painterResource(R.drawable.icon_telegram),
                            contentDescription = "",
                            modifier = Modifier,
                        )
                    },
                    endIcon = {
                        Icon(
                            painter = painterResource(R.drawable.arrow_up_right),
                            contentDescription = "",
                            modifier = Modifier
                        )
                    }
                ) {

                }
                ItemProfileTask(
                    text = stringResource(id = R.string.contact_number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    startIcons = {
                        Icon(
                            painter = painterResource(R.drawable.ic_phone),
                            contentDescription = "",
                            modifier = Modifier
                        )

                    },
                    endIcon = {
                        Icon(
                            painter = painterResource(R.drawable.arrow_up_right),
                            contentDescription = "",
                            modifier = Modifier
                        )
                    }
                ) {
                    openDialer(context,"998883410807".formatPhoneNumber())
                }
            }
        }
    }
    if (showDialog) {
        CustomDialog(
            message = "Logout",
            yes = "Logout",
            no = "Cancel",
            onDismiss = { showDialog = false },
            onConfirm = { intent.invoke(ProfilePageContract.Intent.ClickLogout) })
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White, darkIcons = true)
}