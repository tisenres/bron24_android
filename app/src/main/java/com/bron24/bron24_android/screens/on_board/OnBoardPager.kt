package com.bron24.bron24_android.screens.on_board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.on_board.pages.HowItWorksScreen1
import com.bron24.bron24_android.screens.on_board.pages.HowItWorksScreen2
import com.bron24.bron24_android.screens.on_board.pages.HowItWorksScreen3
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

class OnBoardPager : Screen {
    @Composable
    override fun Content() {
        val viewModel: OnBoardPagerContract.ViewModel = getViewModel<OnBoardPagerVM>()
        val uiState = viewModel.collectAsState()
        OnBoardPagerContent(state = uiState, intent = viewModel::onDispatchers)
    }

}


@Composable
fun OnBoardPagerContent(
    state: State<OnBoardPagerContract.UIState>,
    intent: (OnBoardPagerContract.Intent) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                when (page) {
                    0 -> HowItWorksScreen1()
                    1 -> HowItWorksScreen2()
                    2 -> HowItWorksScreen3()
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            // Skip button
            if (pagerState.currentPage != 2) {
                TextButton(
                    onClick = { intent.invoke(OnBoardPagerContract.Intent.ClickMoveTo) },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        text = stringResource(id = R.string.skip),
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            lineHeight = 17.15.sp,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }

            // Centered Pager Indicator
            PagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.align(Alignment.Center)
            )

            // Next button
            TextButton(
                onClick = {
                    if (pagerState.currentPage < 2) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        intent.invoke(OnBoardPagerContract.Intent.ClickMoveTo)
                    }
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(
                    text = if (pagerState.currentPage == 2) stringResource(id = R.string.get_started) else stringResource(
                        id = R.string.next
                    ),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        lineHeight = 17.15.sp,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}

@Composable
fun PagerIndicator(pagerState: PagerState, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.tertiary else Color(0xFFD9D9D9)
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .size(10.dp)
                    .background(color = color, shape = CircleShape)
            )
        }
    }
}