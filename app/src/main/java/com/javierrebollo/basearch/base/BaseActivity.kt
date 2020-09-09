package com.javierrebollo.basearch.base

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

abstract class BaseActivity : AppCompatActivity() {
    protected val TAG: String = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val navHostFragment = supportFragmentManager.fragments.first() as? NavHostFragment
        if (navHostFragment != null) {
            val childFragments = navHostFragment.childFragmentManager.fragments
            childFragments.forEach { fragment ->
                fragment.onActivityResult(requestCode, resultCode, data)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    abstract fun bindData()
}