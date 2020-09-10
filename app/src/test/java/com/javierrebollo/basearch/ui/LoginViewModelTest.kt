package com.javierrebollo.basearch.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.javierrebollo.basearch.BaseApplication
import com.javierrebollo.basearch.base.ErrorType
import com.javierrebollo.basearch.domain.entity.TaskResult
import com.javierrebollo.basearch.domain.entity.Token
import com.javierrebollo.basearch.domain.usecase.LoginUseCase
import com.javierrebollo.basearch.getOrAwaitValue
import com.javierrebollo.basearch.ui.screens.login.LoginFragmentDirections
import com.javierrebollo.basearch.ui.screens.login.LoginVM
import com.javierrebollo.basearch.utils.NavigationCommand
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class LoginViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val mainThreadSurrogate = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    private val context: Context = getInstrumentation().targetContext.applicationContext

    private lateinit var viewModel: LoginVM

    @RelaxedMockK
    private lateinit var getLoginUseCase: LoginUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(mainThreadSurrogate)
        BaseApplication.instance = mockk<BaseApplication>()
        every { BaseApplication.instance.getString(any()) } returns ""
        viewModel = LoginVM(getLoginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun performSuccessLogin() {
        coEvery {
            getLoginUseCase.invoke("", "")
        } returns TaskResult.SuccessResult<Token>(anyString())

        viewModel.performLogin()

        val response = viewModel.navigation.getOrAwaitValue()

        Assert.assertEquals(
            response,
            NavigationCommand.To(LoginFragmentDirections.fromLoginToUserList())
        )
    }

    @Test
    fun performFailureLogin() {
        coEvery {
            getLoginUseCase.invoke(anyString(), anyString())
        } returns TaskResult.ErrorResult<Token>(Exception())

        runBlocking {

            viewModel.performLogin()
            val errorType: ErrorType = viewModel.errorNotifier.getOrAwaitValue()

            Assert.assertEquals(
                errorType,
                ErrorType.LoginFail(anyString())
            )
        }
    }
}