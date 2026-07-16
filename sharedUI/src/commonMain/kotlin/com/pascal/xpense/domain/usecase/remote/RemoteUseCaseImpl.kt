package com.pascal.xpense.domain.usecase.remote

import com.pascal.xpense.data.remote.dtos.ChatTurn
import com.pascal.xpense.data.repository.remote.RemoteRepository
import org.koin.core.annotation.Single

@Single
class RemoteUseCaseImpl(
    private val repository: RemoteRepository
): RemoteUseCase {

    override suspend fun chatAI(message: List<ChatTurn>): String {
        return repository.chatAI(message)
    }

}