package com.javierrebollo.basearch.data.network

import com.javierrebollo.basearch.BuildConfig
import com.javierrebollo.basearch.exception.ExceptionCode
import com.javierrebollo.basearch.exception.MyAppException
import com.javierrebollo.basearch.utils.Tracker
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.json.JSONObject

abstract class BaseServerRequest<T> {

    abstract val type: Type
    abstract val needAuthorization: Boolean
    abstract val path: String
    val httpUrl: HttpUrl
        get() = "${BuildConfig.BASE_URL}${path}".toHttpUrl()

    @Throws(Exception::class)
    abstract fun parse(response: String, tracker: Tracker): T

    enum class Type {
        POST, GET
    }

    @Throws(MyAppException::class)
    protected fun String.toJsonAndCheckServerError(): JSONObject {
        with(JSONObject(this)) {
            if (!getBoolean("success")) {
                if (has("error")) {
                    with(getJSONObject("error")) {
                        throw MyAppException(
                            ExceptionCode.fromServerError(getInt("errorCode")),
                            getString("userMessage")
                        )
                    }
                } else {
                    throw MyAppException(
                        ExceptionCode.fromServerError(getInt("successCode")),
                        getString("userMessage")
                    )
                }
            } else {
                return this
            }
        }
    }

}