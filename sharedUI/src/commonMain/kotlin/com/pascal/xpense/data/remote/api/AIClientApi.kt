package com.pascal.xpense.data.remote.api

import com.pascal.xpense.BuildKonfig
import com.pascal.xpense.data.remote.dtos.ChatResponse
import com.pascal.xpense.data.remote.dtos.ChatTurn
import com.pascal.xpense.domain.mapper.toChatRequest
import com.pascal.xpense.utils.Constant
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Single

@Single
open class AIClientApi(
    private val client: HttpClient
) {
    suspend fun chatAI(messages: List<ChatTurn>): String {
        val request = messages.toChatRequest(Constant.AI_MODEL)
        val response = client.post("${BuildKonfig.BASE_URL}completions") {
            setBody(request)
        }.body<ChatResponse>()

        return response.choices.firstOrNull()?.message?.content
            ?: response.error?.message
            ?: throw Exception("Empty response from AI")
    }
}
