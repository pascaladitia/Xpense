package com.pascal.xpense.data.remote.api

import com.pascal.xpense.BuildKonfig
import com.pascal.xpense.data.remote.client.client
import com.pascal.xpense.data.remote.dtos.ChatResponse
import com.pascal.xpense.data.remote.dtos.ChatTurn
import com.pascal.xpense.data.remote.mapper.toChatRequest
import com.pascal.xpense.utils.Constant
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class AIService {

    suspend fun chat(messages: List<ChatTurn>): String {
        val request = messages.toChatRequest(Constant.AI_MODEL)
        return try {
            val response: HttpResponse = client.post(Constant.AI_BASE_URL) {
                header("Authorization", "Bearer ${BuildKonfig.AI_API_KEY}")
                setBody(request)
            }
            response.body<ChatResponse>().let { body ->
                if (body.error != null) {
                    throw Exception(body.error.message ?: "AI returned an error")
                }
                body.choices.firstOrNull()?.message?.content?.trim().orEmpty()
            }
        } catch (e: ClientRequestException) {
            throw Exception("AI request failed (${e.response.status}): ${e.message}")
        } catch (e: ServerResponseException) {
            throw Exception("AI server error (${e.response.status})")
        } catch (e: Exception) {
            throw Exception("Failed to reach AI: ${e.message}")
        }
    }
}
