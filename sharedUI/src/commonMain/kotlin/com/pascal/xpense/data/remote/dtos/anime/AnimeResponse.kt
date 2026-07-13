package com.pascal.xpense.data.remote.dtos.anime

import kotlinx.serialization.Serializable

@Serializable
data class AnimeResponse(
    val ongoing: AnimeSectionResponse? = null,
    val completed: AnimeSectionResponse? = null
)

@Serializable
data class AnimeSectionResponse(
    val href: String? = null,
    val otakudesuUrl: String? = null,
    val animeList: List<AnimeItemResponse> = emptyList()
)

@Serializable
data class AnimeItemResponse(
    val title: String? = null,
    val poster: String? = null,
    val episodes: Int? = null,

    val releaseDay: String? = null,
    val latestReleaseDate: String? = null,

    val lastReleaseDate: String? = null,
    val score: String? = null,

    val animeId: String? = null,
    val href: String? = null,
    val otakudesuUrl: String? = null
)