package com.pascal.xpense.data.remote.dtos

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val status: String? = null,
    val creator: String? = null,
    val statusCode: Int? = null,
    val statusMessage: String? = null,
    val message: String? = null,
    val ok: Boolean? = null,
    val data: T? = null,
    val pagination: Pagination? = null
)

@Serializable
data class Pagination(
    val currentPage: Int? = null,
    val totalPage: Int? = null,
    val totalData: Int? = null
)
