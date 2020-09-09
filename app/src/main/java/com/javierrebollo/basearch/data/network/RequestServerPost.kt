package com.javierrebollo.basearch.data.network

import org.json.JSONObject

abstract class RequestServerPost<T> : BaseServerRequest<T>() {
    override val type: Type
        get() = Type.POST

    abstract fun buildBody(): JSONObject
}