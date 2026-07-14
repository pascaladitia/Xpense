package com.pascal.xpense.data.remote.api

import com.pascal.xpense.BuildKonfig
import com.pascal.xpense.utils.Constant
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject

class AIService {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }

    private val endpoint = Constant.AI_BASE_URL
    private val apiKey = BuildKonfig.BYNARA_API_KEY
    private val model = Constant.AI_MODEL

    suspend fun chat(messages: List<ChatTurn>): String {
        val body = buildJsonObject {
            put("model", model)
            putJsonArray("messages") {
                messages.forEach { turn ->
                    add(
                        buildJsonObject {
                            put("role", turn.role)
                            if (turn.imageDataUrl != null) {
                                putJsonArray("content") {
                                    add(
                                        buildJsonObject {
                                            put("type", "text")
                                            put("text", turn.text.ifBlank { "Look at this image and describe it." })
                                        }
                                    )
                                    add(
                                        buildJsonObject {
                                            put("type", "image_url")
                                            putJsonObject("image_url") {
                                                put("url", turn.imageDataUrl)
                                            }
                                        }
                                    )
                                }
                            } else {
                                put("content", turn.text)
                            }
                        }
                    )
                }
            }
        }.toString()

        return try {
            val response: HttpResponse = client.post(endpoint) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $apiKey")
                setBody(body)
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

@Serializable
data class ChatResponse(
    val choices: List<ChatChoice> = emptyList(),
    val error: ApiError? = null
)

@Serializable
data class ChatChoice(
    val message: ApiMessage = ApiMessage(),
    val finish_reason: String? = null
)

@Serializable
data class ApiMessage(
    val role: String = "",
    val content: String = ""
)

@Serializable
data class ApiError(
    val message: String? = null
)
