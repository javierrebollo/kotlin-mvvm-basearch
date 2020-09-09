package com.javierrebollo.basearch.ui.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.javierrebollo.basearch.DependencyInjector
import com.javierrebollo.basearch.base.BaseFragment
import com.javierrebollo.basearch.base.ErrorType
import com.javierrebollo.basearch.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashVM, SplashVMFactory>() {
    override val viewModel: SplashVM by viewModels {
        buildViewModelFactory()
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)

    override fun initComponents() {
    }

    override fun addListeners() {
    }

    override fun addObservers() {
    }

    override fun errorHandler(errorType: ErrorType) {
    }

    override fun buildViewModelFactory(): SplashVMFactory = DependencyInjector.provideSplashVMFactory()

}