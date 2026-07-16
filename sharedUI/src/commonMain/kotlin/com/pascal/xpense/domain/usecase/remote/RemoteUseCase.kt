package com.pascal.xpense.domain.usecase.remote

import com.pascal.xpense.data.remote.dtos.ChatTurn

interface RemoteUseCase {
    suspend fun chatAI(message: List<ChatTurn>): String
}