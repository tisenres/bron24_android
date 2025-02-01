package com.bron24.bron24_android.screens.menu_pages.map_page

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

object BitmapCache {
    private val cache = mutableMapOf<Int, Bitmap>()

    fun getBitmap(context: Context, @DrawableRes resId: Int): Bitmap {
        return cache.getOrPut(resId) {
            getBitmapFromVectorDrawable(context, resId)
        }
    }

    private fun getBitmapFromVectorDrawable(context: Context, @DrawableRes resId: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context, resId) ?: return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}
