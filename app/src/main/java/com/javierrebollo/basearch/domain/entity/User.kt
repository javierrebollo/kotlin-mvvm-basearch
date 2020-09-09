package com.javierrebollo.basearch.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Parcelize
@Keep
data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val gender: Gender,
    val locationLatitude: Double,
    val locationLongitude: Double,
    val smallAvatar: String,
    val bigAvatar: String
) : Parcelable, Serializable