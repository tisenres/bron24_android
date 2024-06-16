package com.bron24.bron24_android.features.language.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.features.language.domain.Language

val robotoFontFamily = FontFamily(
    Font(resId = R.font.gilroy_regular, weight = FontWeight.Normal),
    Font(resId = R.font.gilroy_bold, weight = FontWeight.Bold)
)

@Composable
fun LanguageSelectionScreen(viewModel: LanguageViewModel) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 80.dp, start = 24.dp, end = 24.dp, bottom = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = "Bron24",
                style = TextStyle(
                    fontFamily = robotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.secondary
                ),
            )

            Text(
                text = "Dastur tilini tanlang",
                style = TextStyle(
                    fontFamily = robotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    lineHeight = 64.sp
                ),
                modifier = Modifier.padding(top = 24.dp)
            )

            LanguageOption(
                language = "O'zbek",
                isSelected = selectedLanguage == Language.UZBEK,
                onClick = { viewModel.selectLanguage(Language.UZBEK) },
                modifier = Modifier
                    .padding(top = 80.dp)
            )

            LanguageOption(
                language = "Russian",
                isSelected = selectedLanguage == Language.RUSSIAN,
                onClick = { viewModel.selectLanguage(Language.RUSSIAN) },
                modifier = Modifier
                    .padding(top = 16.dp)
            )
        }

        ConfirmButton(
            isEnabled = true,
            onClick = {
                viewModel.confirmLanguageSelection()
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
    Text(
        text = language,
        style = TextStyle(
            color = if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surface,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = robotoFontFamily
        ),
        modifier = modifier
            .clickable(onClick = onClick)
    )
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
            contentColor = if (isEnabled) Color.White else MaterialTheme.colorScheme.surface,
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        enabled = isEnabled,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            text = "Keyingi",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFontFamily
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SimpleComposablePreview() {
//    Bron24_androidTheme {
//        LanguageSelectionScreen()
//    }
//}
