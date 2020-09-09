package com.javierrebollo.basearch.utils.extensions

import android.content.res.AssetManager
import com.javierrebollo.basearch.BaseApplication
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

fun String.readAssetFile(): String {
    var json = ""
    try {
        val assetManager: AssetManager = BaseApplication.instance.assets
        val inputStream: InputStream = assetManager.open(this)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer, Charset.forName("UTF-8"))

    } catch (e: IOException) {
        e.printStackTrace()
    }
    return json

}