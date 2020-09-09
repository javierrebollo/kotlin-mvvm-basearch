package com.javierrebollo.basearch.utils.extensions

import org.json.JSONArray
import org.json.JSONObject

fun JSONArray.asObjects(): Sequence<JSONObject> {
    return (0 until length()).asSequence().map { getJSONObject(it) }
}

fun JSONArray.getStringList(): List<String> {
    return (0 until length()).asSequence().map { getString(it) }.toList()
}
