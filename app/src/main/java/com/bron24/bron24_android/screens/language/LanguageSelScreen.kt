package com.bron24.bron24_android.screens.language

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.user.Language
import com.bron24.bron24_android.helper.util.setLanguage
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import org.orbitmvi.orbit.compose.collectAsState

class LanguageSelScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: LanguageSelContract.ViewModel = getViewModel<LanguageSelVM>()
        val state = viewModel.collectAsState()
        remember {
            viewModel.initData()
        }
        LanguageScreenContent(state = state, intent = viewModel::onDispatchers)
    }
}
@SuppressLint("RememberReturnType")
@Composable
fun LanguageScreenContent(
    state: State<LanguageSelContract.UISate>,
    intent: (LanguageSelContract.Intent) -> Unit
) {
    var triggerRecomposition by remember { mutableStateOf(false) }
    val context = LocalContext.current
    setLanguage(state.value.selectedLanguage,context)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 30.dp, end = 24.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier
                    .padding(start = 24.dp)
                    .height(32.dp),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 26.sp,
                    color = Color(0xFF32B768),
                    lineHeight = 31.85.sp,
                ),
            )

            Text(
                text = stringResource(id = R.string.select_language),
                modifier = Modifier
                    .padding(top = 16.dp, start = 24.dp, bottom = 30.dp, end = 90.dp)
                    .height(128.dp),
                style = TextStyle(
                    fontFamily = gilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp,
                    color = Color(0xFF060606),
                    lineHeight = 64.sp
                ),
            )
            LazyColumn {
                items(state.value.languages.size){index->
                    val contextX = LocalContext.current
                    LanguageOption(
                        language = state.value.languages[index],
                        isSelected = state.value.selectedLanguage.languageCode == state.value.languages[index].languageCode,
                        onClick = {language->
                            intent.invoke(LanguageSelContract.Intent.SelectedLanguage(language = language))
                            triggerRecomposition = true
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }

        ConfirmButton(
            text = stringResource(id = R.string.next),
            isEnabled = true,
            onClick = {
                intent.invoke(LanguageSelContract.Intent.ClickMoveTo)
            },
        )
    }
}
@Composable
@Preview(showBackground = true)
fun ContentPreview(){
}


@Composable
fun LanguageOption(
    language: Language,
    isSelected: Boolean,
    onClick: (Language) -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedColor by animateColorAsState(
        targetValue = when {
            isSelected -> Color(0xFF26A045)
            else -> Color(0xFFE4E4E4)
        },
        label = ""
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        tryAwaitRelease()
                        onClick(language)
                    }
                )
            },
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(48.dp)
                .background(
                    if (isSelected) Color(0xFF26A045) else Color.Transparent,
                    shape = RoundedCornerShape(3.dp)
                ),
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = language.languageName,
            style = TextStyle(
                color = animatedColor,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gilroyFontFamily,
                lineHeight = 64.sp
            ),
        )
    }
}

@Composable
fun ConfirmButton(
    text:String,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = ButtonDefaults.buttonColors(
        containerColor = if (isEnabled) Color(0xFF26A045) else Color(0xFFE4E4E4),
        contentColor = Color.White,
        disabledContainerColor = Color(0xFFE4E4E4),
        disabledContentColor = Color.Gray
    )

    Button(
        onClick = onClick,
        enabled = isEnabled,
        shape = RoundedCornerShape(8.dp),
        colors = colors,
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(start = 24.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = gilroyFontFamily,
            color = if (isEnabled) Color.White else Color.Gray,
            lineHeight = 32.sp
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun SimpleComposablePreview() {
    LanguageScreenContent(state = mutableStateOf(LanguageSelContract.UISate()), intent = {})
}