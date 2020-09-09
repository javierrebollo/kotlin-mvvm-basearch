package com.javierrebollo.basearch

import androidx.databinding.DataBindingUtil
import com.javierrebollo.basearch.base.BaseActivity
import com.javierrebollo.basearch.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    override fun bindData() {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

}