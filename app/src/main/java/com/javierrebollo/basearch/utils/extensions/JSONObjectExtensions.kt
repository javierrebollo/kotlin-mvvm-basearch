package com.javierrebollo.basearch.utils.extensions

import org.json.JSONObject

fun JSONObject?.getStringOrNull(key: String): String? {
    return this?.let {
        if (isNull(key) || getString(key) == "null") {
            null
        } else {
            getString(key)
        }
    }
}

fun JSONObject?.getLongOrNull(key: String): Long? {
    return this?.let {
        if (isNull(key)) {
            null
        } else {
            getLong(key)
        }
    }
}

fun JSONObject?.getIntOrNull(key: String): Int? {
    return this?.let {
        if (isNull(key)) {
            null
        } else {
            getInt(key)
        }
    }
}

fun JSONObject?.getDoubleOrNull(key: String): Double? {
    return this?.let {
        if (isNull(key)) {
            null
        } else {
            getDouble(key)
        }
    }
}

fun JSONObject?.getBooleanOrNull(key: String): Boolean? {
    return this?.let {
        if (isNull(key)) {
            null
        } else {
            getBoolean(key)
        }
    }
}


fun JSONObject?.getJSONObjectOrNull(key: String): JSONObject? {
    return this?.let {
        if (isNull(key)) {
            null
        } else {
            getJSONObject(key)
        }
    }
}
