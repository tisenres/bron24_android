package com.bron24.bron24_android.components.toast

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import kotlinx.coroutines.delay

@Composable
fun CustomToast(
    message: String,
    type: ToastType,
    onDismiss: () -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(key1 = message) {
        if (type == ToastType.ERROR) {
            vibrate(context)
        }
        when (type) {
            ToastType.SUCCESS -> delay(1000)
            ToastType.INFO -> delay(5000)
            else -> delay(3000)
        }
        isVisible = false
        onDismiss()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .border(
                        width = 2.dp,
                        color = when (type) {
                            ToastType.SUCCESS -> Color(0xFF32B768)
                            ToastType.ERROR -> Color(0xFFFF6B6B)
                            ToastType.WARNING -> Color(0xFFFFA726)
                            ToastType.INFO -> Color(0xFF2196F3)
                        },
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
//                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = when (type) {
                            ToastType.SUCCESS -> Icons.Default.Done
                            ToastType.ERROR -> Icons.Default.Clear
                            ToastType.WARNING -> Icons.Default.Notifications
                            ToastType.INFO -> Icons.Default.Info
                        },
                        contentDescription = "Toast icon",
                        tint = when (type) {
                            ToastType.SUCCESS -> MaterialTheme.colorScheme.tertiary
                            ToastType.ERROR -> Color(0xFFFF6B6B)
                            ToastType.WARNING -> Color(0xFFFFA726)
                            ToastType.INFO -> Color(0xFF2196F3)
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Text(
                        text = message,
                        style = TextStyle(
                            fontFamily = gilroyFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            color = Color.Black,
                            lineHeight = 20.sp,
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically),
                    )
                }
            }
        }
    }
}

@Composable
fun ObserveToast() {
    val toastData by ToastManager.toastData.collectAsState()

    toastData?.let { data ->
        CustomToast(
            message = data.message,
            type = data.type,
            onDismiss = {
                ToastManager.clearToast()
            }
        )
    }
}


private fun vibrate(context: Context) {
    val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)
    vibrator?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            it.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            it.vibrate(200)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    CustomToast(
        message = "Internet is not avaisdsdsdsdsdsdsdsdsdsdsdsdsdsdsdlable",
        type = ToastType.ERROR,
        onDismiss = {})
}
