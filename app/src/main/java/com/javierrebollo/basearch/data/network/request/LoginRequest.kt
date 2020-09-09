package com.javierrebollo.basearch.data.network.request

import com.javierrebollo.basearch.data.network.RequestServerPost
import com.javierrebollo.basearch.data.network.parseToken
import com.javierrebollo.basearch.domain.entity.Token
import com.javierrebollo.basearch.utils.Tracker
import org.json.JSONObject

class LoginRequest(
    val username: String,
    val password: String
) : RequestServerPost<Token>() {

    override val needAuthorization: Boolean
        get() = false

    override val path: String
        get() = "login"

    override fun buildBody(): JSONObject {
        return JSONObject().apply {
            put("user", username)
            put("password", password)
        }
    }

    override fun parse(response: String, tracker: Tracker): Token {
        return JSONObject(response).parseToken()
    }
}