package com.javierrebollo.basearch.data.network

import com.javierrebollo.basearch.data.helper.NetworkStatusHelper
import com.javierrebollo.basearch.data.helper.SharedPreferencesHelper
import com.javierrebollo.basearch.domain.entity.TaskResult
import com.javierrebollo.basearch.exception.WithoutConnectivityException
import com.javierrebollo.basearch.utils.Tracker
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.util.*

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

        return try {
            TaskResult.SuccessResult(executeWithExceptions(baseServerRequest, extraHeaders))
        } catch (exception: Exception) {
            TaskResult.ErrorResult(exception)
        }
    }

    private fun <T> executeWithExceptions(
        baseServerRequest: BaseServerRequest<T>,
        extraHeaders: HashMap<String, String>? = null
    ): T {
        val requestBuilder = baseBuilder

        if (networkStatusHelper.isOnline) {

            if (baseServerRequest.needAuthorization) {
                requestBuilder.header("Authorization", "${sharedPreferencesHelper.token}")
            }

            val url = baseServerRequest.httpUrl
            Tracker.logDebug(TAG, "Call to: ${url.toUri()}")

            if (extraHeaders != null) {
                for ((key, value) in extraHeaders) {
                    requestBuilder.addHeader(key, value)
                }
            }

            requestBuilder.url(url).apply {
                if (baseServerRequest is RequestServerPost && baseServerRequest.type == BaseServerRequest.Type.POST) {
                    val stringBody = baseServerRequest.buildBody().toString()
                    Tracker.logDebug(TAG, stringBody)
                    post(RequestBody.create(JSON, stringBody))
                }
            }

            var response: Response? = null

            try {
                response = client.newCall(requestBuilder.build()).execute()
                response.body.use { responseBody ->
                    val responseString = responseBody!!.source().readUtf8()
                    Tracker.logDebug(TAG, responseString)

                    return baseServerRequest.parse(responseString, Tracker)
                }

            } catch (e: Exception) {
                Tracker.logWarning(
                    Tracker.SERVER_REQUEST_ERROR, TAG, "Request onFailure " + e.localizedMessage, e
                )
                throw e
            }

        } else {
            throw WithoutConnectivityException("Without connection")
        }
    }
}
