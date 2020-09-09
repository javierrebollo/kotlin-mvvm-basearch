package com.javierrebollo.basearch.ui.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.javierrebollo.basearch.DependencyInjector
import com.javierrebollo.basearch.base.BaseFragment
import com.javierrebollo.basearch.base.ErrorType
import com.javierrebollo.basearch.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginVM, LoginVMFactory>() {
    override val viewModel: LoginVM by viewModels {
        buildViewModelFactory()
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun initComponents() {
    }

    override fun addListeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.performLogin()
        }
    }

    override fun addObservers() {
        binding.viewModel = viewModel
    }

    override fun errorHandler(errorType: ErrorType) {
        when (errorType) {
            is ErrorType.LoginFail -> Toast.makeText(context, errorType.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun buildViewModelFactory(): LoginVMFactory = DependencyInjector.provideLoginVMFactory()
}