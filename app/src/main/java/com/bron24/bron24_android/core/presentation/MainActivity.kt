package com.bron24.bron24_android.core.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.bron24.bron24_android.core.presentation.theme.Bron24_androidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider {
                Bron24_androidTheme {
                    MainScreen()
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Recreate your activity or refresh the UI elements as needed
        setContent {
            CompositionLocalProvider {
                Bron24_androidTheme {
                    MainScreen()
                }
            }
        }
    }
}