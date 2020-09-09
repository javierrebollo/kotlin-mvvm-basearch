package com.javierrebollo.basearch.data.network.responsehandler

import com.javierrebollo.basearch.data.network.request.GetUsersRequest
import com.javierrebollo.basearch.utils.extensions.readAssetFile

class GetUsersRequestHandler {
    companion object {
        fun handler(request: GetUsersRequest): String {
            return "get_users_request-success.json".readAssetFile()
        }
    }
}