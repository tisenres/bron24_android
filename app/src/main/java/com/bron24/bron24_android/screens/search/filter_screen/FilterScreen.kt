package com.bron24.bron24_android.screens.search.filter_screen

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.common.FilterOptions
import com.bron24.bron24_android.components.items.AppButton
import com.bron24.bron24_android.components.items.CheckBox
import com.bron24.bron24_android.components.items.CustomAppBar
import com.bron24.bron24_android.components.items.ItemInfosData
import com.bron24.bron24_android.components.items.ItemProfileTask
import com.bron24.bron24_android.components.items.ItemSelectedData
import com.bron24.bron24_android.components.items.RangeSlider
import com.bron24.bron24_android.helper.util.formatDate
import com.bron24.bron24_android.helper.util.formatMoney
import com.bron24.bron24_android.helper.util.formatTime
import com.bron24.bron24_android.screens.main.theme.BgColorF3
import com.bron24.bron24_android.screens.main.theme.Black
import com.bron24.bron24_android.screens.main.theme.Black17
import com.bron24.bron24_android.screens.main.theme.GrayLight
import com.bron24.bron24_android.screens.main.theme.GrayLighter
import com.bron24.bron24_android.screens.main.theme.Success
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePageContract
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import java.util.Locale


data class FilterScreen(val block: (FilterOptions) -> Unit) : Screen {
    @Composable
    override fun Content() {
        val viewModel: FilterScreenContract.ViewModel = getViewModel<FilterScreenVM>()
        val uiState = viewModel.collectAsState()
//        FilterScreenContent(block = block, state = uiState, intent = viewModel::onDispatchers)
    }

}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreenContent(
    state: State<HomePageContract.UIState>,
    intent: (HomePageContract.Intent) -> Unit,
    clickBack: () -> Unit,
    resend: () -> Unit,
    filterOptions: (FilterOptions) -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = White, darkIcons = true)

    var rangeTime by remember { mutableStateOf(state.value.filter.rangeTime) }
    var rangeSumma by remember { mutableStateOf(state.value.filter.rangeSumma) }


    var openDialog by remember {
        mutableStateOf(false)
    }

    var dateState by remember {
        mutableStateOf(
            DatePickerState(
                locale = Locale.ROOT
            )
        )
    }
    val coroutineScope = rememberCoroutineScope()
    var selectLocation by remember {
        mutableIntStateOf(state.value.filter.selectLocation)
    }
    var selectedDate by remember { mutableStateOf(state.value.filter.selectedDate) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    var showBottomSheet by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {

        CustomAppBar(
            title = stringResource(id = R.string.filter),
            startIcons = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "icons",
                    tint = Black17,
                    modifier = Modifier.size(24.dp)
                )
            },
            actions = {
                TextButton(onClick = {
                    rangeTime = 0.0f..1f
                    rangeSumma = 0.0f..1f
                    intent.invoke(
                        HomePageContract.Intent.FilterIntent(
                            filter = HomePageContract.FilterUiState()
                        )
                    )
                    resend.invoke()
                }) {
                    Text(
                        text = stringResource(id = R.string.reset),
                        fontFamily = gilroyFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Success
                    )
                }
            }
        ) {
            clickBack.invoke()
        }
        if (openDialog) {
            DatePickerDialog(
                colors = DatePickerDefaults.colors(containerColor = White),
                onDismissRequest = {
                    openDialog = false
                    dateState.selectedDateMillis?.let { millis ->
                        selectedDate = formatDate(millis)
                    }
                },
                confirmButton = { }
            ) {
                DatePicker(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(40.dp),
                    title = {
                        Row(modifier = Modifier
                            .fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Bron24",
                                modifier = Modifier,
                                color = Success,
                                fontFamily = gilroyFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            TextButton(onClick = {
                                dateState.selectedDateMillis?.let { millis ->
                                    intent.invoke(
                                        HomePageContract.Intent.FilterIntent(
                                            filter = state.value.filter.copy(
                                                selectedDate = formatDate(
                                                    millis
                                                )
                                            )
                                        )
                                    )
                                }
                                openDialog = false
                            }, shape = RoundedCornerShape(10.dp), modifier = Modifier
                                .height(40.dp)
                                .align(Alignment.CenterVertically),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Success,
                                    contentColor = White
                                )
                                ) {
                                Text(text = stringResource(id = R.string.save), fontFamily = gilroyFontFamily, color = White)
                            }
                        }

                    },
                    headline = {
                        Text(
                            text = stringResource(id = R.string.change_date),
                            modifier = Modifier.padding(start = 24.dp, bottom = 16.dp),
                            color = Success,
                            fontFamily = gilroyFontFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                    },
                    showModeToggle = false,
                    state = dateState,
                    colors = DatePickerDefaults.colors(
                        titleContentColor = Success,
                        containerColor = White,
                        selectedDayContentColor = White,
                        dayInSelectionRangeContentColor = White,
                        selectedDayContainerColor = Success,
                        dayInSelectionRangeContainerColor = Success,
                        todayDateBorderColor = Success,
                        todayContentColor = Success,
                        dayContentColor = Black,
                        weekdayContentColor = Black,
                        yearContentColor = Black,
                        selectedYearContainerColor = Success,
                        headlineContentColor = Success,
                        currentYearContentColor = Black,
                    ),
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = stringResource(id = R.string.date),
                fontSize = 16.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium,
                color = Black,
                modifier = Modifier.padding(top = 12.dp)
            )
            ItemSelectedData(
                date = state.value.filter.selectedDate,
                hint = stringResource(id = R.string.any_date),
                modifier = Modifier,
                endIcon = {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "",
                        tint = GrayLight
                    )
                }) {
                openDialog = true
            }
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.time),
                    fontSize = 16.sp,
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Black,
                    modifier = Modifier.weight(1f)
                )
                Row(
                    modifier = Modifier
                        .background(color = BgColorF3, shape = RoundedCornerShape(6.dp))
                        .width(110.dp)
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.value.filter.startTime,
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = GrayLight,
                        fontSize = 14.sp
                    )
                    Text(
                        text = " - ",
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = GrayLight,
                        fontSize = 14.sp
                    )
                    Text(
                        text = state.value.filter.endTime,
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = GrayLight,
                        fontSize = 14.sp
                    )
                }
            }

            RangeSlider(
                range = state.value.filter.rangeTime,
                onRangeChange = { newRange ->
                    intent.invoke(
                        HomePageContract.Intent.FilterIntent(
                            filter = state.value.filter.copy(
                                rangeTime = newRange,
                                startTime = formatTime(newRange.start),
                                endTime = formatTime(newRange.endInclusive)
                            )
                        )
                    )
                },
                thumbSize = 16.dp,
                paddingVertical = 16.dp
            )

            Text(
                text = stringResource(id = R.string.location),
                fontSize = 16.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium,
                color = Black,
                modifier = Modifier.padding(top = 24.dp)
            )
            ItemSelectedData(
                date = state.value.filter.location,
                hint = stringResource(id = R.string.choose_district),
                modifier = Modifier,
                endIcon = {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "",
                        tint = GrayLight
                    )
                }
            ) {
                showBottomSheet = true
            }
            val list = listOf(
                "Bektemir",
                "Chilonzor",
                "Mirobod",
                "Mirzo Ulug‘bek",
                "Sergeli",
                "Shayxontohur",
                "Uchtepa",
                "Olmazor",
                "Yakkasaroy",
                "Yunusobod",
                "Yashnobod",
                "Yangihayot"
            )
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState,
                    modifier = Modifier.defaultMinSize(minHeight = 300.dp),
                    containerColor = Color.White
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(bottom = 12.dp)
                        ) {
                            items(count = list.size) {
                                ItemProfileTask(
                                    text = list[it],
                                    borderColor = if (it == selectLocation) Success else GrayLighter,
                                    index = it,
                                    paddingHor = 0.dp
                                ) {
                                    selectLocation = it
                                    intent.invoke(
                                        HomePageContract.Intent.FilterIntent(
                                            filter = state.value.filter.copy(
                                                location = list[it]
                                            )
                                        )
                                    )
                                }
                            }
                        }
                        AppButton(
                            text = stringResource(id = R.string.save_lpcation),
                            modifier = Modifier
                        ) {
                            coroutineScope.launch {
                                sheetState.hide()
                                showBottomSheet = false
                            }
                        }
                    }
                }
            }

            Text(
                text = stringResource(id = R.string.price_range),
                fontSize = 16.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium,
                color = Black,
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                ItemSelectedData(
                    date = state.value.filter.minSumma.toString(),
                    hint = "min",
                    modifier = Modifier.weight(0.2f),
                    endIcon = {
                        Text(
                            text = stringResource(id = R.string.som),
                            fontSize = 13.sp,
                            fontFamily = gilroyFontFamily,
                            color = GrayLighter
                        )
                    }
                ) {
                    //open
                }
                Spacer(modifier = Modifier.width(16.dp))
                ItemSelectedData(
                    date = state.value.filter.maxSumma.toString(),
                    hint = "max",
                    modifier = Modifier.weight(0.2f),
                    endIcon = {
                        Text(
                            text = stringResource(id = R.string.som),
                            fontSize = 13.sp,
                            fontFamily = gilroyFontFamily,
                            color = GrayLighter
                        )
                    }
                ) {
                    //open
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "0",
                    fontSize = 12.sp,
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Success,
                    modifier = Modifier.padding(top = 20.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "1M",
                    fontSize = 12.sp,
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Success,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
            RangeSlider(
                range = state.value.filter.rangeSumma,
                onRangeChange = { newRange ->
                    intent.invoke(
                        HomePageContract.Intent.FilterIntent(
                            filter = state.value.filter.copy(
                                rangeSumma = newRange,
                                minSumma = formatMoney(newRange.start),
                                maxSumma = formatMoney(newRange.endInclusive)
                            )
                        )
                    )
                },
                thumbSize = 16.dp,
                paddingVertical = 16.dp
            )
            Text(
                text = stringResource(id = R.string.venue_type),
                fontSize = 16.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium,
                color = Black,
                modifier = Modifier.padding(top = 20.dp, bottom = 4.dp)
            )
            CheckBox(stringResource(id = R.string.outdoor), state.value.filter.selOutdoor) {
                intent.invoke(
                    HomePageContract.Intent.FilterIntent(
                        filter = state.value.filter.copy(
                            selOutdoor = it
                        )
                    )
                )

            }
            CheckBox(stringResource(id = R.string.indoor), state.value.filter.selIndoor) {
                intent.invoke(
                    HomePageContract.Intent.FilterIntent(
                        filter = state.value.filter.copy(
                            selIndoor = it
                        )
                    )
                )
            }
            Text(
                text = stringResource(id = R.string.infrastructure),
                fontSize = 16.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium,
                color = Black,
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    ItemInfosData(
                        date = stringResource(id = R.string.parking),
                        hint = "",
                        select = state.value.filter.selParking,
                        modifier = Modifier,
                        topIcon = {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(color = Success, shape = RoundedCornerShape(12)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_local_parking_24),
                                    contentDescription = "icon",
                                    tint = White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    ) {
                        intent.invoke(
                            HomePageContract.Intent.FilterIntent(
                                filter = state.value.filter.copy(
                                    selParking = it
                                )
                            )
                        )
                    }
                }
                item {
                    ItemInfosData(
                        date = stringResource(id = R.string.changing_room),
                        hint = "",
                        select = state.value.filter.selRoom,
                        modifier = Modifier,
                        topIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.mingcute_coathanger_fill),
                                contentDescription = "icon",
                                tint = Success,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    ) {
                        intent.invoke(
                            HomePageContract.Intent.FilterIntent(
                                filter = state.value.filter.copy(
                                    selRoom = it
                                )
                            )
                        )
                    }
                }
                item {
                    ItemInfosData(
                        date = stringResource(id = R.string.shower),
                        hint = "",
                        select = state.value.filter.selShower,
                        modifier = Modifier,
                        topIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_shower_24),
                                contentDescription = "icon",
                                tint = Success,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    ) {
                        intent.invoke(
                            HomePageContract.Intent.FilterIntent(
                                filter = state.value.filter.copy(
                                    selShower = it
                                )
                            )
                        )
                    }
                }
            }
            AppButton(text = stringResource(id = R.string.see_result), modifier = Modifier) {
                filterOptions.invoke(
                    FilterOptions(
                        state.value.filter.selParking,
                        state.value.filter.selRoom,
                        state.value.filter.selShower,
                        state.value.filter.selOutdoor,
                        state.value.filter.selIndoor,
                        state.value.filter.startTime,
                        state.value.filter.endTime,
                        state.value.filter.minSumma,
                        state.value.filter.maxSumma,
                        state.value.filter.location,
                        state.value.filter.selectedDate
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterScreenPreview() {
}

