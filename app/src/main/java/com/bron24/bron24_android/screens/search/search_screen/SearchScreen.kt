package com.bron24.bron24_android.screens.search.search_screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.VenueItem
import com.bron24.bron24_android.components.items.VenueLoadingPlaceholder
import com.bron24.bron24_android.screens.main.theme.GrayLight
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsContract
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsScreenContent
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState

class SearchScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: SearchScreenContract.ViewModel = getViewModel<SearchScreenVM>()
        val uiState = viewModel.collectAsState()
        SearchScreenContent(state = uiState, viewModel::onDispatchers)
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun SearchScreenContent(
    state: State<SearchScreenContract.UIState>,
    intent: (SearchScreenContract.Intent) -> Unit
) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = White, darkIcons = true)

    val focusManager = LocalFocusManager.current
    var query by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var openDetails by remember {
        mutableStateOf(false)
    }

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose {
            focusManager.clearFocus()
        }
    }
    BackHandler {
        if (openDetails) {
            openDetails = false
        } else {
            intent.invoke(SearchScreenContract.Intent.Back)
        }
    }
    if (openDetails) {
        val venues = mutableStateOf(
            VenueDetailsContract.UIState(
                isLoading = state.value.isLoading,
                venue = state.value.venueDetails,
                imageUrls = state.value.imageUrls
            )
        )
        VenueDetailsScreenContent(state =venues, back = { openDetails = false }) {
            intent.invoke(SearchScreenContract.Intent.ClickOrder(it))
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
        ) {
            SearchBarSection(
                query = query,
                focusManager = focusManager,
                focusRequester = focusRequester,
                onQueryChanged = { query = it },
                onSearch = {
                    intent.invoke(SearchScreenContract.Intent.Search(query))
                },
                onCancelClick = {
                    openDetails = false
                    intent.invoke(SearchScreenContract.Intent.Back)
                }
            )
            Text(
                text = stringResource(R.string.search_here),
                modifier = Modifier.padding(start = 16.dp),
                fontSize = 20.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            if (!state.value.isLoading && state.value.searchResult.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.not_found),
                        contentDescription = "icon",
                        modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = stringResource(R.string.not_found),
                        fontSize = 14.sp,
                        fontFamily = gilroyFontFamily,
                        color = GrayLight,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    if (state.value.isLoading) {
                        items(5) {
                            VenueLoadingPlaceholder()
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    } else {
                        items(state.value.searchResult) { venue ->
                            VenueItem(venue = venue, onFavoritesClick = {
                                openDetails = true
                                intent.invoke(SearchScreenContract.Intent.OpenVenueDetails(it.venueId))

                            })
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBarSection(
    query: String,
    focusManager: FocusManager,
    focusRequester: FocusRequester,
    onQueryChanged: (String) -> Unit,
    onSearch: () -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomSearchField(
            query = query,
            onQueryChanged = onQueryChanged,
            onSearch = onSearch,
            focusManager = focusManager,
            focusRequester = focusRequester,
            modifier = Modifier.weight(1f)
        )
        TextButton(
            onClick = onCancelClick,
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.tertiary)
        ) {
            Text(
                stringResource(R.string.cancel),
                fontSize = 14.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Composable
private fun CustomSearchField(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    focusRequester: FocusRequester
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(38.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(10.dp)
            )
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp)
            .focusRequester(focusRequester)
            .clickable { focusRequester.requestFocus() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_search_green),
                contentDescription = "Search Icon",
                tint = MaterialTheme.colorScheme.tertiary
            )
            Spacer(modifier = Modifier.width(8.dp)) // Spacing between icon and text
            BasicTextField(
                value = query,
                onValueChange = onQueryChanged,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    lineHeight = 17.sp
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        onSearch()
                    }
                ),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_stadium),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = gilroyFontFamily,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                lineHeight = 17.sp
                            ),
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier.weight(1f),
            )
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChanged("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Icon",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}