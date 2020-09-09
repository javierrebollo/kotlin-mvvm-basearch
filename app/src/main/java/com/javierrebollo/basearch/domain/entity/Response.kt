package com.javierrebollo.basearch.domain.entity

sealed class TaskResult<T> {
    data class ErrorResult<T>(val exception: Exception) : TaskResult<T>()
    data class SuccessResult<T>(val value: T) : TaskResult<T>()
}

inline fun <T, R> TaskResult<T>.on(
    success: (T) -> R,
    failure: (Exception) -> R
): R? {
    return when (this) {
        is TaskResult.SuccessResult -> success(this.value)
        is TaskResult.ErrorResult -> failure(this.exception)
    }
}