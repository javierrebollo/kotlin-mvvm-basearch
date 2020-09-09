package com.javierrebollo.basearch.utils

import android.util.Log
import com.javierrebollo.basearch.exception.MyAppException

object Tracker {
    val SERVER_REQUEST_ERROR = ErrorId("Server request error")
    val STATE_ERROR = ErrorId("State error")
    val NETWORK_STATE_CALLBACK = ErrorId("Network state callback")

    fun logError(errorId: ErrorId, tag: String, message: String, exception: Throwable? = null) {
        logError(errorId, message, exception, tag, false)
    }

    fun logWarning(errorId: ErrorId, tag: String, message: String, exception: Throwable? = null) {
        logError(errorId, message, exception, tag, true)
    }

    private fun logError(
        errorId: ErrorId,
        message: String,
        exception: Throwable?,
        tag: String,
        warning: Boolean
    ) {
        exception?.printStackTrace()

        val msg = "${errorId.value} - $message" +
            when {
                exception is MyAppException -> "\n$exception"
                exception != null -> "\n${exception.javaClass.simpleName}: ${exception.message}"
                else -> ""
            }

        if (warning) {
            Log.w(tag, msg)
        } else {
            Log.e(tag, msg)
        }
    }

    fun logInfo(tag: String, message: String) {
        Log.i(tag, message)
    }

    fun logDebug(tag: String, message: String) {
        Log.d(tag, message)
    }

    fun trackIsInForeground(isInForeground: Boolean, tag: String) {
        val message = "App went to " + if (isInForeground) "foreground" else "background"
        logDebug(tag, message)
    }
}

inline class ErrorId(val value: String)