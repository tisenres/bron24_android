package com.bron24.bron24_android.screens.search.search_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.common.FilterOptions
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.venuelisting.VenueCard
class SearchScreen:Screen{
    @Composable
    override fun Content() {
        val viewModel:SearchScreenContract.ViewModel = getViewModel<SearchScreenVM>()
    }

}

@Composable
fun SearchScreenContent(
    state:State<SearchScreenContract.UIState>,
    intent:(SearchScreenContract.Intent)->Unit
) {
    val focusManager = LocalFocusManager.current
    var query by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose {
            focusManager.clearFocus()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBarSection(
            query = query,
            focusManager = focusManager,
            focusRequester = focusRequester,
            onQueryChanged = { query = it },
            onSearch = {

            },
            onCancelClick = {

            }
        )
        Text(
            text = "Search Result",
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 20.sp,
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (state.value.isLoading) {
                items(5) { // Arbitrary number of placeholders
                    VenueCard(venue = null, isLoading = true,)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            } else {
                items(state.value.searchResult) { venue ->
                    VenueCard(
                        venue = venue,
                        isLoading = false,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
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
            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF32B768))
        ) {
            Text(
                "Cancel",
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
                tint = Color(0xFF32B768)
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
                            text = "Search here...",
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