package com.javierrebollo.basearch.usecase

import com.javierrebollo.basearch.data.repository.SessionRepository
import com.javierrebollo.basearch.domain.entity.TaskResult
import com.javierrebollo.basearch.domain.entity.Token
import com.javierrebollo.basearch.domain.usecase.LoginUseCase
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

    private val mainThreadSurrogate = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @RelaxedMockK
    private lateinit var sessionRepository: SessionRepository

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(mainThreadSurrogate)
        loginUseCase = LoginUseCase(sessionRepository)
    }

    @ObsoleteCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun successLogin() {
        val token: Token = "091ijo1n0i4h1i10ub4u"
        val expectedResult = TaskResult.SuccessResult(token)

        every {
            sessionRepository.login(anyString(), anyString())
        } returns expectedResult

        every {
            sessionRepository.token = anyString()
        } just Runs

        runBlocking {
            val response = loginUseCase.invoke(anyString(), anyString())

            verify(exactly = 1) {
                sessionRepository.token = match { it == token }
            }

            Assert.assertEquals(
                response,
                expectedResult
            )
        }
    }

    @Test
    fun performFailureLogin() {
        val expectedResult = TaskResult.ErrorResult<Token>(Exception())

        every {
            sessionRepository.login(anyString(), anyString())
        } returns expectedResult

        every {
            sessionRepository.token = anyString()
        } just Runs

        runBlocking {
            val response = loginUseCase.invoke(anyString(), anyString())

            verify(exactly = 0) {
                sessionRepository.token
            }

            Assert.assertEquals(
                response,
                expectedResult
            )
        }
    }
}