package com.javierrebollo.basearch.utils.extensions

import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.javierrebollo.basearch.BaseApplication

fun @receiver:FontRes Int.getTypeface(): Typeface? {
    return ResourcesCompat.getFont(BaseApplication.instance, this)
}

fun @receiver:ColorRes Int.getColor(): Int {
    return ContextCompat.getColor(BaseApplication.instance, this)
}

fun @receiver:StringRes Int.getString(): String {
    return BaseApplication.instance.getString(this)
}

fun @receiver:StringRes Int.getString(vararg formatArgs: Any): String {
    return BaseApplication.instance.getString(this, *formatArgs)
}

fun @receiver:PluralsRes Int.getPlural(quantity: Int, vararg formatArgs: Any): String {
    return BaseApplication.instance.resources.getQuantityString(this, quantity, *formatArgs)
}

fun @receiver:DrawableRes Int.getDrawable(): Drawable {
    return AppCompatResources.getDrawable(BaseApplication.instance, this)!!
}

fun @receiver:DrawableRes Int.getBitmap(): Bitmap {
    return AppCompatResources.getDrawable(BaseApplication.instance, this)!!.toBitmap()
}

fun @receiver:DrawableRes Int.getBitmapTinted(@ColorRes color: Int): Bitmap {
    return AppCompatResources.getDrawable(BaseApplication.instance, this)!!.toBitmap(color)
}

fun @receiver:DimenRes Int.getDimension(): Float {
    return BaseApplication.instance.resources.getDimension(this)
}

fun @receiver:DimenRes Int.getDimensionPixelOffset(): Int {
    return BaseApplication.instance.resources.getDimensionPixelOffset(this)
}

fun @receiver:DimenRes Int.getDimensionPixelSize(): Int {
    return BaseApplication.instance.resources.getDimensionPixelSize(this)
}