package com.javierrebollo.basearch.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

sealed class NavigationCommand {
    data class To(val directions: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
    data class BackTo(val destinationId: Int) : NavigationCommand()
    data class BackWithResult(val resultKey: String, val result: Parcelable) : NavigationCommand()
    object ToRoot : NavigationCommand()
    data class WithArgs(
        val directions: NavDirections,
        val args: Bundle,
        val navOptions: NavOptions
    ) : NavigationCommand()
}