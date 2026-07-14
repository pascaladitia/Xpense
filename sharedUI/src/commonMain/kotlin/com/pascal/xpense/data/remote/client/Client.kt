package com.pascal.xpense.data.remote.client

import co.touchlab.kermit.Logger
import com.pascal.xpense.utils.Constant
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val client: HttpClient by lazy {
    HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = false
                }
            )
        }
        install(Logging) {
            logger = object : io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
                    Logger.d(tag = "HttpClient") { message }
                }
            }
            level = LogLevel.INFO
        }
        install(HttpTimeout) {
            requestTimeoutMillis = Constant.TIMEOUT
            connectTimeoutMillis = Constant.TIMEOUT
            socketTimeoutMillis = Constant.TIMEOUT
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }
}
