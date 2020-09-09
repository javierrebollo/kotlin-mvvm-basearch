package com.javierrebollo.basearch.data.repository

import com.javierrebollo.basearch.data.helper.SharedPreferencesHelper
import com.javierrebollo.basearch.data.network.ServerClient
import com.javierrebollo.basearch.data.network.request.LoginRequest
import com.javierrebollo.basearch.domain.entity.TaskResult
import com.javierrebollo.basearch.domain.entity.Token

private const val TAG: String = "SessionRepository"

class SessionRepository(
    private val serverClient: ServerClient,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) {

    var token: Token?
        get() = sharedPreferencesHelper.token
        set(value) {
            sharedPreferencesHelper.token = value
        }

    fun login(username: String, password: String): TaskResult<Token> {
        return serverClient.execute(
            LoginRequest(
                username,
                password
            )
        )
    }

    companion object {

        @Volatile private var instance: SessionRepository? = null

        fun getInstance(
            serverClient: ServerClient,
            sharedPreferencesHelper: SharedPreferencesHelper
        ) =
            instance ?: synchronized(this) {
                instance ?: SessionRepository(
                    serverClient,
                    sharedPreferencesHelper
                ).also { instance = it }
            }
    }
}