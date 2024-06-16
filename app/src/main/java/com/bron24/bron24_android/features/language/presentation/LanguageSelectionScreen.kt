package com.bron24.bron24_android.features.language.presentation

import android.app.Application
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.core.presentation.theme.Bron24_androidTheme
import com.bron24.bron24_android.features.language.domain.Language

val robotoFontFamily = FontFamily(
    Font(resId = R.font.gilroy_regular, weight = FontWeight.Normal),
    Font(resId = R.font.gilroy_bold, weight = FontWeight.Bold)
)

@Composable
fun LanguageSelectionScreen(
    viewModel: LanguageViewModel,
    onNavigateToLocationRequest: () -> Unit
) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 80.dp, end = 24.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = "Bron24",
                modifier = Modifier
                    .padding(start = 24.dp)
                    .height(32.dp)
                    .width(78.dp),
                style = TextStyle(
                    fontFamily = robotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    lineHeight = 32.sp
                ),
            )

            Text(
                text = "Dastur tilini tanlang",
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp)
                    .height(128.dp),
                style = TextStyle(
                    fontFamily = robotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    lineHeight = 64.sp
                ),
            )

            LanguageOption(
                language = "O‘zbek",
                isSelected = selectedLanguage == Language.UZBEK,
                onClick = { viewModel.selectLanguage(Language.UZBEK) },
                modifier = Modifier
                    .padding(top = 80.dp)
            )

            LanguageOption(
                language = "Russian",
                isSelected = selectedLanguage == Language.RUSSIAN,
                onClick = { viewModel.selectLanguage(Language.RUSSIAN) },
            )
        }

        ConfirmButton(
            isEnabled = selectedLanguage != null,
            onClick = {
                viewModel.confirmLanguageSelection()
                onNavigateToLocationRequest()
            }
        )
    }
}

@Composable
fun LanguageOption(
    language: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }

    val animatedColor by animateColorAsState(
        targetValue = if (isPressed) MaterialTheme.colorScheme.primary else if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surface,
        label = ""
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                        onClick()
                    }
                )
            }
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(64.dp)
                .background(
                    if (isSelected) MaterialTheme.colorScheme.tertiary else Color.Transparent,
                    shape = RoundedCornerShape(3.dp)
                )
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = language,
            modifier = Modifier
                .height(64.dp),
            style = TextStyle(
                color = animatedColor,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = robotoFontFamily,
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
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        enabled = isEnabled,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(start = 24.dp)
    ) {
        Text(
            text = "Keyingi",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFontFamily,
            color = if (isEnabled) Color.White else Color.Gray
        )
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    Bron24_androidTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            LanguageSelectionScreen(viewModel = LanguageViewModel(Application())) {

            }
        }
    }
}
