package com.javierrebollo.basearch.data.repository

import com.javierrebollo.basearch.data.network.ServerClient
import com.javierrebollo.basearch.data.network.request.GetUsersRequest
import com.javierrebollo.basearch.domain.entity.TaskResult
import com.javierrebollo.basearch.domain.entity.User

private const val TAG: String = "UserRepository"

class UserRepository(
    private val serverClient: ServerClient
) {

    fun getUsers(): TaskResult<List<User>> {
        return serverClient.execute(
            GetUsersRequest()
        )
    }

    companion object {

        @Volatile private var instance: UserRepository? = null

        fun getInstance(
            serverClient: ServerClient
        ) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(
                    serverClient
                ).also { instance = it }
            }
    }
}