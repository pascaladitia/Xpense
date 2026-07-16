package com.pascal.xpense.utils.base

import com.pascal.xpense.data.remote.dtos.BaseResponse
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.bodyAsText
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

abstract class SafeApiCall {

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        isLenient = true
    }

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): T {
        return safeApiCallInternal(apiCall = apiCall)
    }

    private suspend fun <T> safeApiCallInternal(
        apiCall: suspend () -> T
    ): T {
        return try {
            apiCall()
        } catch (e: Exception) {
            val response = when (e) {
                is ClientRequestException -> e.response
                is ServerResponseException -> e.response
                else -> null
            }
            val errorBody = response?.bodyAsText()
            val message = when (e) {
                is ClientRequestException,
                is ServerResponseException -> parseError(errorBody)

                is IOException -> "Network error. Please check your connection."

                else -> e.message ?: "Unknown error"
            }
            throw Exception(message)
        }
    }

    private fun parseError(errorBody: String?): String {
        if (errorBody.isNullOrBlank()) return "Unknown error"
        return try {
            val baseResponse = json.decodeFromString<BaseResponse<JsonElement>>(errorBody)
            baseResponse.message?.takeIf { it.isNotBlank() } ?: "Unknown error"
        } catch (_: Exception) {
            "Unknown error"
        }
    }
}
