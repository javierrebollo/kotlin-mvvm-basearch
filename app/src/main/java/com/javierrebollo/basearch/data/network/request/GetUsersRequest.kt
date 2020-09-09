package com.javierrebollo.basearch.data.network.request

import com.javierrebollo.basearch.data.network.RequestServerGet
import com.javierrebollo.basearch.data.network.parseUser
import com.javierrebollo.basearch.domain.entity.User
import com.javierrebollo.basearch.utils.Tracker
import com.javierrebollo.basearch.utils.extensions.asObjects
import org.json.JSONArray

class GetUsersRequest : RequestServerGet<List<User>>() {

    override val needAuthorization: Boolean
        get() = true

    override val path: String
        get() = "getUsers"

    override fun parse(response: String, tracker: Tracker): List<User> {
        return JSONArray(response)
            .asObjects()
            .mapNotNull { it.parseUser() }
            .toList()
    }
}