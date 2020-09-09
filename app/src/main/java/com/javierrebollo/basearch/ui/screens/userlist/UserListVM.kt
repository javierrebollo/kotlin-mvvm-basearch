package com.javierrebollo.basearch.ui.screens.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.javierrebollo.basearch.base.BaseViewModel
import com.javierrebollo.basearch.base.BaseViewModelFactory
import com.javierrebollo.basearch.domain.entity.User
import com.javierrebollo.basearch.domain.entity.on
import com.javierrebollo.basearch.domain.usecase.GetUsersUseCase

class UserListVM(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    val userList: LiveData<List<User>> = liveData {
        showLoading()
        getUsersUseCase.invoke()
            .on(
                success = {
                    emit(it)
                    hideLoading()
                },
                failure = {
                    emit(emptyList())
                    hideLoading()
                }
            )
    }
}

class UserListVMFactory(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModelFactory<UserListVM>() {
    override fun buildViewModel(): UserListVM {
        return UserListVM(
            getUsersUseCase
        )
    }
}