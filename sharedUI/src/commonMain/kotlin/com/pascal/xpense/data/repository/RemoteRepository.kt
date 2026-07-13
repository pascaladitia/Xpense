package com.pascal.xpense.data.repository

import com.pascal.xpense.data.remote.dtos.BaseResponse
import com.pascal.xpense.data.remote.dtos.anime.AnimeResponse
import com.pascal.xpense.data.remote.dtos.anime.AnimeSectionResponse

interface RemoteRepository {
    suspend fun getAnimeHome() : BaseResponse<AnimeResponse>
    suspend fun getAnimeLive(page: Int): BaseResponse<AnimeSectionResponse>
}