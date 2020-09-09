package com.javierrebollo.basearch

import android.app.Application
import com.javierrebollo.basearch.utils.Foreground

class BaseApplication : Application() {
    companion object {
        lateinit var instance: BaseApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Foreground.init(this)
            .addListener(object : Foreground.Listener {
                override fun onBecameForeground() {
                }

                override fun onBecameBackground() {
                }
            })
    }
}