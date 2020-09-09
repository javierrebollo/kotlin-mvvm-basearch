package com.javierrebollo.basearch.domain.entity

import android.os.Parcelable
import com.javierrebollo.basearch.exception.ExceptionCode
import com.javierrebollo.basearch.exception.MyAppException
import kotlinx.android.parcel.Parcelize


sealed class Gender(val value: String) : Parcelable {
    @Parcelize
    object Male : Gender("Male")

    @Parcelize
    object Female : Gender("Female")

    companion object {
        fun fromString(value: String): Gender {
            return when (value) {
                "Male" -> Male
                "Female" -> Female
                else -> throw MyAppException(ExceptionCode.NOT_FOUND, "Match not found")
            }
        }
    }
}