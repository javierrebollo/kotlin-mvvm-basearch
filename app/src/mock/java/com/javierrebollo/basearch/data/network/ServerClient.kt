package com.javierrebollo.basearch.data.network

import com.javierrebollo.basearch.data.helper.NetworkStatusHelper
import com.javierrebollo.basearch.data.helper.SharedPreferencesHelper
import com.javierrebollo.basearch.data.network.request.GetUsersRequest
import com.javierrebollo.basearch.data.network.request.LoginRequest
import com.javierrebollo.basearch.data.network.responsehandler.GetUsersRequestHandler
import com.javierrebollo.basearch.data.network.responsehandler.LoginRequestHandler
import com.javierrebollo.basearch.domain.entity.TaskResult
import com.javierrebollo.basearch.exception.ExceptionCode
import com.javierrebollo.basearch.exception.MyAppException
import com.javierrebollo.basearch.utils.Tracker
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request

private val JSON = "application/json; charset=utf-8".toMediaType()

class ServerClient(
    private val client: OkHttpClient,
    private val networkStatusHelper: NetworkStatusHelper,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) {
    private val TAG: String = this::class.java.simpleName

    private val baseBuilder: Request.Builder
        get() = Request.Builder()
            .header("Accept", "application/json")

    fun <T> execute(
        baseServerRequest: BaseServerRequest<T>,
        extraHeaders: HashMap<String, String>? = null
    ): TaskResult<T> {

        try {
            val jsonResponse: String = when (baseServerRequest) {
                is LoginRequest -> LoginRequestHandler.handler(baseServerRequest)
                is GetUsersRequest -> GetUsersRequestHandler.handler(baseServerRequest)
                else -> throw MyAppException(ExceptionCode.NOT_FOUND, "Path not found")
            }

            return TaskResult.SuccessResult(
                baseServerRequest.parse(
                    jsonResponse,
                    Tracker
                ) as T
            )

        } catch (exception: Exception) {
            return TaskResult.ErrorResult(exception)
        }
    }
}
