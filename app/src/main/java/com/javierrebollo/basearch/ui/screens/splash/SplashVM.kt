package com.javierrebollo.basearch.ui.screens.splash

import android.os.Handler
import androidx.lifecycle.viewModelScope
import com.javierrebollo.basearch.base.BaseViewModel
import com.javierrebollo.basearch.base.BaseViewModelFactory
import com.javierrebollo.basearch.domain.entity.on
import com.javierrebollo.basearch.domain.usecase.IsLoggedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashVM(
    private val isLoggedUseCase: IsLoggedUseCase
) : BaseViewModel() {

    init {
        Handler().postDelayed({
            viewModelScope.launch(Dispatchers.Default) {
                isLoggedUseCase.invoke().on(
                    success = {
                        withContext(Dispatchers.Main) {
                            if (it) {
                                transitionToUserList()
                            } else {
                                transitionToLogin()
                            }
                        }
                    },
                    failure = {
                        withContext(Dispatchers.Main) {
                            transitionToLogin()
                        }
                    }
                )
            }
        }, 2_000)
    }

    private fun transitionToLogin() {
        goTo(SplashFragmentDirections.fromSplashToLogin())
    }

    private fun transitionToUserList() {
        goTo(SplashFragmentDirections.fromSplashToUserList())
    }
}

class SplashVMFactory(
    private val isLoggedUseCase: IsLoggedUseCase
) : BaseViewModelFactory<SplashVM>() {
    override fun buildViewModel(): SplashVM {
        return SplashVM(
            isLoggedUseCase
        )
    }
}