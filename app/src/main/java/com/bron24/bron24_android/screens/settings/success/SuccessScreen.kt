package com.bron24.bron24_android.screens.settings.success

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.Black
import com.bron24.bron24_android.screens.main.theme.Green
import com.bron24.bron24_android.screens.main.theme.Purple
import com.bron24.bron24_android.screens.main.theme.White
import org.orbitmvi.orbit.compose.collectAsState

object SuccessScreen : Screen {
    private fun readResolve(): Any = SuccessScreen

    @Composable
    override fun Content() {
        val viewModel: SuccessScreenContract.ViewModel = getViewModel<SuccessScreenVM>()
        val state = viewModel.collectAsState()
        SuccessScreenContent(state, viewModel::onDispatchers)
    }
}

@Composable
private fun SuccessScreenContent(
    state: State<SuccessScreenContract.UiState>, intent: (SuccessScreenContract.Intent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.success_booking), contentDescription = "")
        Text(text = "Success!!!", color = Purple, fontFamily = FontFamily(Font(R.font.gilroy_bold)))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = Green)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .align(alignment = Alignment.CenterStart)
            ) {
                Text(text = "Your online queue number", color = Black, fontFamily = FontFamily(Font(R.font.gilroy_regular)))
                Text(text = "63 65 82", color = Black, fontFamily = FontFamily(Font(R.font.gilroy_bold)))
                Text(text = "Bunyodkor kompleksi", color = Black, fontFamily = FontFamily(Font(R.font.gilroy_regular)))
                Text(text = "21.02.2024 09:00", color = Black, fontFamily = FontFamily(Font(R.font.gilroy_regular)))
            }
            Image(
                painter = painterResource(R.drawable.ic_map_cute),
                contentDescription = "",
                alignment = Alignment.CenterEnd,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .padding(10.dp)
                    .background(color = White)
                    .padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "My orders",
            color = White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.gilroy_regular))
        )
        Text(text = "Main page", color = Green, fontFamily = FontFamily(Font(R.font.gilroy_regular)))
    }
}