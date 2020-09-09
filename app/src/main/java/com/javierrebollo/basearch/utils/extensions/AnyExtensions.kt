package com.javierrebollo.basearch.utils.extensions

fun Any?.toSafeString(): String {
    return when {
        this == null -> ""
        this.toString().trim { it <= ' ' } == "" -> ""
        this.toString().trim { it <= ' ' }.equals("null", ignoreCase = true) -> ""
        else -> this.toString().trim { it <= ' ' }
    }
}