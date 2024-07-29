//package com.bron24.bron24_android.screens.map
//
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import androidx.annotation.DrawableRes
//import com.yandex.mapkit.mapview.MapView
//import com.yandex.runtime.image.ImageProvider
//
//class ResourceImageProvider(@DrawableRes private val resourceId: Int) : ImageProvider() {
//    override fun getId(): String {
//        TODO("Not yet implemented")
//    }
//
//    override fun getImage(): Bitmap {
//        val options = BitmapFactory.Options()
//        options.inScaled = false
//        return BitmapFactory.decodeResource(MapView.context.resources, resourceId, options)
//    }
//}