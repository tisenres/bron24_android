package com.bron24.bron24_android.features.language.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.R
import com.bron24.bron24_android.core.presentation.theme.Bron24_androidTheme

val robotoFontFamily = FontFamily(
    Font(resId = R.font.gilroy_regular, weight = FontWeight.Normal),
    Font(resId = R.font.gilroy_bold, weight = FontWeight.Bold)
)

@Composable
fun LanguageSelectionScreen() {
    var selectedLanguage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
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
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .padding(top = 56.dp, bottom = 32.dp)
                        .height(128.dp)
                )

                Text(
                    text = "O'zbek",
                    style = TextStyle(
                        color = if (selectedLanguage == "Uzbek") MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surface,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = robotoFontFamily
                    ),
                    modifier = Modifier
                        .clickable { selectedLanguage = "Uzbek" }
                        .padding(top = 80.dp)
                )

                Text(
                    text = "Russian",
                    style = TextStyle(
                        color = if (selectedLanguage == "Russian") MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surface,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = robotoFontFamily
                    ),
                    modifier = Modifier
                        .clickable { selectedLanguage = "Russian" }
                        .padding(top = 16.dp)
                )
            }

            Button(
                onClick = { /* Handle confirmation */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.surface
                ),
                enabled = selectedLanguage != null,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Keyingi",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = robotoFontFamily,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleComposablePreview() {
    Bron24_androidTheme {
        LanguageSelectionScreen()
    }
}
