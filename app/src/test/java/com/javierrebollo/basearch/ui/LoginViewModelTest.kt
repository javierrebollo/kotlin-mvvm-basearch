package com.javierrebollo.basearch.ui

import androidx.lifecycle.Observer
import com.javierrebollo.basearch.base.ErrorType
import com.javierrebollo.basearch.domain.usecase.LoginUseCase
import com.javierrebollo.basearch.ui.screens.login.LoginVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: LoginVM

    @Mock
    lateinit var loginUseCase: LoginUseCase

    @Mock
    lateinit var alertErrorMessage: Observer<ErrorType>


    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = LoginVM(loginUseCase)
    }

    @ObsoleteCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun performSuccessLogin() {
    }

    @Test
    fun performFailureLogin() {
    }
}