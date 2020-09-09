package com.javierrebollo.basearch.utils.extensions

import android.text.Editable

fun Editable?.toSafeInt(): Int {
    return try {
        toString().toInt()
    } catch (e: Exception) {
        0
    }
}