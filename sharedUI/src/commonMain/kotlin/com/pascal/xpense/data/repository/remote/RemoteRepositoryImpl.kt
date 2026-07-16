package com.pascal.xpense.data.repository.remote

import com.pascal.xpense.data.remote.api.AIClientApi
import com.pascal.xpense.data.remote.dtos.ChatTurn
import com.pascal.xpense.utils.base.SafeApiCall
import org.koin.core.annotation.Single

@Single
class RemoteRepositoryImpl(
    private val api: AIClientApi
): RemoteRepository, SafeApiCall() {

    override suspend fun chatAI(messages: List<ChatTurn>): String =
        safeApiCall { api.chatAI(messages) }

}