package com.javierrebollo.basearch.exception

enum class ExceptionCode {
    UNKNOWN,
    NOT_FOUND;

    companion object {
        fun fromServerError(errorCode: Int): ExceptionCode {
            return when (errorCode) {
                else -> UNKNOWN
            }
        }
    }
}

class MyAppException : Exception {

    val code: ExceptionCode

    constructor(code: ExceptionCode) {
        this.code = code
    }

    constructor(code: ExceptionCode, message: String) : super(message) {
        this.code = code
    }

    override fun toString(): String {
        val message = localizedMessage
        return if (message != null) {
            "MyAppException($code, $message)"
        } else {
            "MyAppException($code)"
        }
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is MyAppException && code == other.code && message == other.message
    }
}

val Exception?.safeLocalizedMessage: String
    get() = this?.localizedMessage ?: "(null)"
