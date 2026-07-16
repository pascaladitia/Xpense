package com.pascal.xpense.data.repository.remote

import com.pascal.xpense.data.remote.dtos.ChatTurn

interface RemoteRepository {
    suspend fun chatAI(messages: List<ChatTurn>): String
}