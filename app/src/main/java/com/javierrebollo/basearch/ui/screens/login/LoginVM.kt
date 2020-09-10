package com.javierrebollo.basearch.ui.screens.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.javierrebollo.basearch.R
import com.javierrebollo.basearch.base.BaseViewModel
import com.javierrebollo.basearch.base.BaseViewModelFactory
import com.javierrebollo.basearch.base.ErrorType
import com.javierrebollo.basearch.domain.entity.on
import com.javierrebollo.basearch.domain.usecase.LoginUseCase
import com.javierrebollo.basearch.utils.extensions.getString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginVM(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    val username = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")

    fun performLogin() {
        viewModelScope.launch {
            loginUseCase(username.value!!, password.value!!).on(
                success = {
                    withContext(Dispatchers.Main) {
                        goTo(LoginFragmentDirections.fromLoginToUserList())
                    }
                    Log.d(TAG, "Result: $it")
                },
                failure = {
                    notifyError(ErrorType.LoginFail(R.string.error_login.getString()))
                    Log.d(TAG, "Fail: ${it.message}")
                }
            )
        }
    }
}

class LoginVMFactory(
    private val loginUseCase: LoginUseCase
) : BaseViewModelFactory<LoginVM>() {
    override fun buildViewModel(): LoginVM {
        return LoginVM(loginUseCase)
    }
}