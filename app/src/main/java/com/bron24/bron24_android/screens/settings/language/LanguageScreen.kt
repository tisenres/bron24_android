package com.bron24.bron24_android.screens.settings.language

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.AppButton
import com.bron24.bron24_android.components.items.CustomAppBar
import com.bron24.bron24_android.components.items.ItemLanguage
import com.bron24.bron24_android.screens.main.theme.Black17
import com.yandex.mapkit.logo.VerticalAlignment


class LanguageScreen : Screen {
    @Composable
    override fun Content() {

    }
}

@Composable
fun LanguageScreenContent() {
    var selectedLanguage by remember {
        mutableIntStateOf(0)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        CustomAppBar(title = "Change language", startIcons = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "icons",
                tint = Black17
            )
        }){}
        LazyColumn(
            contentPadding = PaddingValues(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            items(3) {
                ItemLanguage(text = "uzb", selected = it == selectedLanguage) {
                    selectedLanguage = it
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageScreenPreview() {
    LanguageScreenContent()
}