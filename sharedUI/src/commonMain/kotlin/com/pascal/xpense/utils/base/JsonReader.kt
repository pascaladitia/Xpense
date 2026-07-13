package com.pascal.xpense.utils.base

import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.readResourceBytes

object JsonReader {

    @OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
    suspend inline fun <reified T> load(fileName: String): T {
        val resourcePath = "files/$fileName"
        val bytes = readResourceBytes(resourcePath)
        val text = bytes.decodeToString()
        return Json { ignoreUnknownKeys = true }.decodeFromString(text)
    }
}