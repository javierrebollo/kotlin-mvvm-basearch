package com.javierrebollo.basearch.data.network

import com.javierrebollo.basearch.domain.entity.Gender
import com.javierrebollo.basearch.domain.entity.Token
import com.javierrebollo.basearch.domain.entity.User
import org.json.JSONObject

fun JSONObject.parseToken(): Token {
    return getString("token")
}

fun JSONObject.parseUser(): User {
    return User(
        id = getString("id"),
        firstName = getString("firstName"),
        lastName = getString("lastName"),
        email = getString("email"),
        gender = Gender.fromString(getString("gender")),
        locationLatitude = getDouble("locationLatitude"),
        locationLongitude = getDouble("locationLongitude"),
        smallAvatar = getString("smallAvatar"),
        bigAvatar = getString("bigAvatar")
    )
}