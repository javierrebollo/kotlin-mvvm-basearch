package com.javierrebollo.basearch.data.network.responsehandler

import com.javierrebollo.basearch.data.network.request.LoginRequest
import com.javierrebollo.basearch.utils.extensions.readAssetFile

class LoginRequestHandler {
    companion object {
        fun handler(request: LoginRequest): String {
            return if (request.username == "mock_user"
                && request.password == "mock_password"
            ) {

                "login_request-success.json".readAssetFile()

            } else {
                "login_request-fail.json".readAssetFile()
            }
        }
    }
}