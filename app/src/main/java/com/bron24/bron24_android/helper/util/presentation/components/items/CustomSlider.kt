package com.bron24.bron24_android.helper.util.presentation.components.items

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bron24.bron24_android.screens.main.theme.Success
import com.bron24.bron24_android.screens.main.theme.bgSuccess
import kotlin.math.abs

@Composable
fun RangeSlider(
    range: ClosedFloatingPointRange<Float>,
    onRangeChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier,
    thumbSize: Dp = 12.dp,
    trackHeight: Dp = 4.dp,
    paddingVertical: Dp
) {
    var startThumb by remember { mutableStateOf(range.start) }
    var endThumb by remember { mutableStateOf(range.endInclusive) }

    val thumbRadius = with(LocalDensity.current) { thumbSize.toPx() / 2 }
    val trackColor = bgSuccess
    val activeTrackColor = Success

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(thumbSize)
            .padding(horizontal = thumbSize/2, paddingVertical)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    val position = change.position.x
                    val width = size.width

                    // Thumb pozitsiyalarini aniqlash
                    val newValue = (position / width).coerceIn(0f, 1f)

                    if (abs(newValue - startThumb) < abs(newValue - endThumb)) {
                        startThumb = newValue.coerceAtMost(endThumb) // Oldingi thumbni yangilash
                    } else {
                        endThumb = newValue.coerceAtLeast(startThumb) // Keyingi thumbni yangilash
                    }

                    onRangeChange(startThumb..endThumb) // Hodisani chaqirish
                }
            }
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            // To'liq track
            drawLine(
                color = trackColor,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = trackHeight.toPx()
            )

            // Faol track (start va end thumb o'rtasidagi chiziq)
            drawLine(
                color = activeTrackColor,
                start = Offset(startThumb * size.width, size.height / 2),
                end = Offset(endThumb * size.width, size.height / 2),
                strokeWidth = trackHeight.toPx()
            )

            // Start thumb
            drawCircle(
                color = Success,
                radius = thumbRadius,
                center = Offset(startThumb * size.width, size.height / 2)
            )

            // End thumb
            drawCircle(
                color = Success,
                radius = thumbRadius,
                center = Offset(endThumb * size.width, size.height / 2)
            )
        }
    }
}