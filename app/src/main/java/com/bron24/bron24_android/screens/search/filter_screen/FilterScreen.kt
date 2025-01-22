package com.bron24.bron24_android.screens.search.filter_screen

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.common.FilterOptions
import com.bron24.bron24_android.helper.util.formatDate
import com.bron24.bron24_android.helper.util.formatMoney
import com.bron24.bron24_android.helper.util.formatTime
import com.bron24.bron24_android.components.items.AppButton
import com.bron24.bron24_android.components.items.CheckBox
import com.bron24.bron24_android.components.items.CustomAppBar
import com.bron24.bron24_android.components.items.ItemInfosData
import com.bron24.bron24_android.components.items.ItemProfileTask
import com.bron24.bron24_android.components.items.ItemSelectedData
import com.bron24.bron24_android.components.items.RangeSlider
import com.bron24.bron24_android.screens.main.theme.BgColorF3
import com.bron24.bron24_android.screens.main.theme.Black
import com.bron24.bron24_android.screens.main.theme.Black17
import com.bron24.bron24_android.screens.main.theme.GrayLight
import com.bron24.bron24_android.screens.main.theme.GrayLighter
import com.bron24.bron24_android.screens.main.theme.Success
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import java.util.Locale


data class FilterScreen(val block: (FilterOptions) -> Unit) : Screen {
    @Composable
    override fun Content() {
        val viewModel: FilterScreenContract.ViewModel = getViewModel<FilterScreenVM>()
        val uiState = viewModel.collectAsState()
        FilterScreenContent(block = block, state = uiState, intent = viewModel::onDispatchers)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreenContent(
    block: (FilterOptions) -> Unit,
    state: State<FilterScreenContract.UIState>,
    intent: (FilterScreenContract.Intent) -> Unit
) {
    var selParking by remember { mutableStateOf(false) }
    var selRoom by remember { mutableStateOf(false) }
    var selShower by remember { mutableStateOf(false) }

    var selOutdoor by remember {
        mutableStateOf(false)
    }
    var selIndoor by remember {
        mutableStateOf(false)
    }

    var rangeTime by remember { mutableStateOf(0.0f..1f) }
    var rangeSumma by remember { mutableStateOf(0.0f..1f) }

    var startTime by remember {
        mutableStateOf("00:00")
    }
    var endTime by remember {
        mutableStateOf("00:00")
    }

    var minSumma by remember {
        mutableIntStateOf(0)
    }
    var maxSumma by remember {
        mutableIntStateOf(1000000)
    }

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
    var location by remember {
        mutableStateOf("")
    }
    var selectLocation by remember {
        mutableIntStateOf(-1)
    }
    var selectedDate by remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        CustomAppBar(
            title = "Filter",
            startIcons = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "icons",
                    tint = Black17,
                    modifier = Modifier.size(24.dp)
                )
            },
            actions = {
                Text(
                    text = "Reset",
                    fontFamily = gilroyFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Success
                )
            }
        ) {}
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
                    modifier = Modifier.fillMaxSize(),
                    title = {
                        Row {
                            Text(
                                text = "Bron24",
                                modifier = Modifier
                                    .padding(top = 24.dp, start = 24.dp)
                                    .weight(1f),
                                color = Success,
                                fontFamily = gilroyFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            AppButton(
                                text = "Save",
                                modifier = Modifier
                                    .padding(top = 16.dp, end = 24.dp)
                                    .width(90.dp)
                                    .height(36.dp)
                            ) {
                                dateState.selectedDateMillis?.let { millis ->
                                    selectedDate = formatDate(millis)
                                }
                                openDialog = false
                            }
                        }

                    },
                    headline = {
                        Text(
                            text = "Kerakli kunni tanlang",
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
                text = "Date",
                fontSize = 16.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium,
                color = Black,
                modifier = Modifier.padding(top = 12.dp)
            )
            ItemSelectedData(
                date = selectedDate,
                hint = "Any date",
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
                    text = "Time",
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
                        text = startTime,
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
                        text = endTime,
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = GrayLight,
                        fontSize = 14.sp
                    )
                }
            }

            RangeSlider(
                range = rangeTime,
                onRangeChange = { newRange ->
                    rangeTime = newRange
                    startTime = formatTime(newRange.start)
                    endTime = formatTime(newRange.endInclusive)
                },
                thumbSize = 16.dp,
                paddingVertical = 16.dp
            )

            Text(
                text = "Location",
                fontSize = 16.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium,
                color = Black,
                modifier = Modifier.padding(top = 24.dp)
            )
            ItemSelectedData(
                date = location,
                hint = "Choose district",
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
                    modifier = Modifier.defaultMinSize(minHeight = 300.dp)
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
                                    location = list[it]
                                }
                            }
                        }
                        AppButton(
                            text = "Save location",
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
                text = "Price range",
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
                    date = minSumma.toString(),
                    hint = "min",
                    modifier = Modifier.weight(0.2f),
                    endIcon = {
                        Text(
                            text = "so\'m",
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
                    date = maxSumma.toString(),
                    hint = "max",
                    modifier = Modifier.weight(0.2f),
                    endIcon = {
                        Text(
                            text = "so\'m",
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
                range = rangeSumma,
                onRangeChange = { newRange ->
                    rangeSumma = newRange
                    minSumma = formatMoney(newRange.start)
                    maxSumma = formatMoney(newRange.endInclusive)
                },
                thumbSize = 16.dp,
                paddingVertical = 16.dp
            )
            Text(
                text = "Venue type",
                fontSize = 16.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium,
                color = Black,
                modifier = Modifier.padding(top = 20.dp, bottom = 4.dp)
            )
            CheckBox("Outdoor", selOutdoor) { selOutdoor = !selOutdoor }
            CheckBox("Indoor", selIndoor) { selIndoor = !selIndoor }
            Text(
                text = "Infrastructure",
                fontSize = 16.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium,
                color = Black,
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    ItemInfosData(
                        date = "Parking",
                        hint = "",
                        select = selParking,
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
                        selParking = !selParking
                    }
                }
                item {
                    ItemInfosData(
                        date = "Changing room",
                        hint = "",
                        select = selRoom,
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
                        selRoom = !selRoom
                    }
                }
                item {
                    ItemInfosData(
                        date = "Shower",
                        hint = "",
                        select = selShower,
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
                        selShower = !selShower
                    }
                }
            }
            AppButton(text = "See Results", modifier = Modifier) {
                intent.invoke(FilterScreenContract.Intent.ClickBack)
                block.invoke(
                    FilterOptions(
                        selectedDate,
                        minSumma,
                        maxSumma,
                        selRoom or selIndoor or selShower or selParking or selOutdoor,
                        district = location
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

