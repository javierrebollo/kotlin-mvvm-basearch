package com.javierrebollo.basearch.domain.usecase

import com.javierrebollo.basearch.data.repository.UserRepository
import com.javierrebollo.basearch.domain.entity.TaskResult
import com.javierrebollo.basearch.domain.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUsersUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): TaskResult<List<User>> {
        return withContext(Dispatchers.IO) {
            return@withContext userRepository.getUsers()
        }
    }
}