package com.javierrebollo.basearch.domain.usecase

import com.javierrebollo.basearch.data.repository.SessionRepository
import com.javierrebollo.basearch.domain.entity.TaskResult
import com.javierrebollo.basearch.domain.entity.Token
import com.javierrebollo.basearch.domain.entity.on
import com.javierrebollo.basearch.exception.safeLocalizedMessage
import com.javierrebollo.basearch.utils.Tracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG: String = "LoginUseCase"

class LoginUseCase(
    private val sessionRepository: SessionRepository
) {

    suspend operator fun invoke(
        username: String,
        password: String
    ): TaskResult<Token> {
        return withContext(Dispatchers.IO) {
            return@withContext sessionRepository.login(username, password)
                .also {
                    it.on(
                        success = { token ->
                            sessionRepository.token = token
                        },
                        failure = { exception ->
                            Tracker.logDebug(TAG, "Login error: ${exception.safeLocalizedMessage}")
                        }
                    )
                }
        }
    }
}