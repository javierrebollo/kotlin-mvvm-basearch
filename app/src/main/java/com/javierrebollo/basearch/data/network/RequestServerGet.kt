package com.javierrebollo.basearch.data.network

abstract class RequestServerGet<T> : BaseServerRequest<T>() {
    override val type: Type
        get() = Type.GET
}