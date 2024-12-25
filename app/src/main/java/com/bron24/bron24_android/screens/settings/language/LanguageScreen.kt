package com.bron24.bron24_android.screens.settings.language

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.components.items.CustomAppBar
import com.bron24.bron24_android.components.items.ItemLanguage
import com.bron24.bron24_android.helper.util.setLanguage
import com.bron24.bron24_android.screens.main.theme.Black17
import org.orbitmvi.orbit.compose.collectAsState


class LanguageScreen : Screen {
  @Composable
  override fun Content() {
    val viewMode: LanguageContract.ViewModel = getViewModel<LanguageVM>()
    remember { viewMode.initData() }
    val state = viewMode.collectAsState()
    LanguageScreenContent(state, viewMode::onDispatchers)
  }
}

@Composable
private fun LanguageScreenContent(state: State<LanguageContract.UIState>, intent: (LanguageContract.Intent) -> Unit) {
  val context = LocalContext.current
  Column(modifier = Modifier.fillMaxSize()) {
    CustomAppBar(title = "Change language", startIcons = {
      Icon(
        imageVector = Icons.Default.ArrowBack, contentDescription = "icons", tint = Black17
      )
    }) {
      intent(LanguageContract.Intent.MoveBack)
    }
    LazyColumn(
      contentPadding = PaddingValues(top = 12.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier.padding(horizontal = 20.dp)
    ) {
      itemsIndexed(state.value.languageList) { index, item ->
        ItemLanguage(text = item.languageName, selected = index == state.value.selectedLanguageIndex) {
          intent(LanguageContract.Intent.ChangeLanguage(item, index))
          setLanguage(item, context)
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun LanguageScreenPreview() {
  LanguageScreen()
}