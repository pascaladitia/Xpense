package com.pascal.xpense.data.remote.dtos

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val message: String? = null,
)