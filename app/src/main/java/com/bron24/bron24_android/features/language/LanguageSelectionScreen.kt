package com.bron24.bron24_android.features.language

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.R
import com.bron24.bron24_android.domain.entity.user.Language
import com.bron24.bron24_android.features.main.theme.Bron24_androidTheme
import com.bron24.bron24_android.features.main.theme.gilroyFontFamily
import com.bron24.bron24_android.helper.util.LocaleManager

@Composable
fun LanguageSelectionScreen(
    viewModel: LanguageViewModel = hiltViewModel(),
    onNavigateToLocationRequest: () -> Unit
) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val availableLanguages by viewModel.availableLanguages.collectAsState()
    val context = LocalContext.current
    var triggerRecomposition by remember { mutableStateOf(false) }

    LaunchedEffect(selectedLanguage) {
        LocaleManager.setLocale(context, selectedLanguage.languageCode)
        triggerRecomposition = !triggerRecomposition
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 30.dp, end = 24.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            if (triggerRecomposition) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier
                        .padding(start = 24.dp)
                        .height(32.dp),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        color = Color(0xFF32B768),
                        lineHeight = 31.85.sp,
                        letterSpacing = (-0.78).sp
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
            } else {
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier
                        .padding(start = 24.dp)
                        .height(32.dp),
                    style = TextStyle(
                        fontFamily = gilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        color = Color(0xFF32B768),
                        lineHeight = 32.sp,
                        letterSpacing = (-0.78).sp
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
            }

            LazyColumn {
                items(availableLanguages) { language ->
                    LanguageOption(
                        language = language,
                        isSelected = selectedLanguage == language,
                        onClick = {
                            viewModel.selectLanguage(language)
                        },
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
        }

        ConfirmButton(
            isEnabled = true,
            onClick = {
                viewModel.confirmLanguageSelection()
                onNavigateToLocationRequest()
            },
        )
    }
}

@Composable
fun LanguageOption(
    language: Language,
    isSelected: Boolean,
    onClick: () -> Unit,
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
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        tryAwaitRelease()
                        onClick()
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
//            modifier = Modifier.height(64.dp),
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
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = if (isEnabled) Color(0xFF26A045) else Color(0xFFE4E4E4)
        ),
        enabled = isEnabled,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(start = 24.dp)
    ) {
        Text(
            text = stringResource(id = R.string.next),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = gilroyFontFamily,
            color = if (isEnabled) Color.White else Color.Gray,
            lineHeight = 32.sp
        )
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    Bron24_androidTheme {
        LanguageSelectionScreen (hiltViewModel()) {}
    }
}