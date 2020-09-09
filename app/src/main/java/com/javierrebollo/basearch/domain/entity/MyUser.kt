package com.javierrebollo.basearch.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

typealias Token = String

@Parcelize
@Keep
data class MyUser(
    val id: String,
    val name: String,
    val email: String,
    val token: Token
) : Parcelable, Serializable