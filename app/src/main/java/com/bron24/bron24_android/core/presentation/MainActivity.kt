package com.bron24.bron24_android.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bron24.bron24_android.core.presentation.theme.Bron24_androidTheme
import com.bron24.bron24_android.features.language.presentation.LanguageMainSwitchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bron24_androidTheme {
                LanguageMainSwitchScreen()
            }
        }
    }
}