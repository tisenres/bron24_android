package com.bron24.bron24_android.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bron24.bron24_android.core.presentation.theme.Bron24_androidTheme
import com.bron24.bron24_android.features.language.presentation.LanguageSelectionScreen

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Bron24_androidTheme {
                LanguageSelectionScreen()
            }
        }
    }
}