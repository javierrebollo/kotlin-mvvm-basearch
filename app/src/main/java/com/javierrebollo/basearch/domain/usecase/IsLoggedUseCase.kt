package com.javierrebollo.basearch.domain.usecase

import com.javierrebollo.basearch.data.repository.SessionRepository
import com.javierrebollo.basearch.domain.entity.TaskResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG: String = "LoginUseCase"

class IsLoggedUseCase(
    private val sessionRepository: SessionRepository
) {

    suspend operator fun invoke(): TaskResult<Boolean> {
        return withContext(Dispatchers.IO) {
            return@withContext TaskResult.SuccessResult(sessionRepository.token != null)
        }
    }
}