package com.javierrebollo.basearch.utils.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.core.graphics.drawable.DrawableCompat

fun Drawable.toBitmap(@ColorRes tint: Int? = null): Bitmap {
    tint?.let { color ->
        DrawableCompat.setTint(this, color)
    }

    if (this is BitmapDrawable) {
        if (bitmap != null) {
            return bitmap
        }
    }

    val bitmap = if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

    } else {
        Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
    }

    val canvas = Canvas(bitmap)
    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    draw(canvas)
    return bitmap
}