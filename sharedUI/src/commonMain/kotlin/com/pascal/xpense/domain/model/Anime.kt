package com.pascal.xpense.domain.model

data class Anime(
    val ongoing: AnimeSection?,
    val completed: AnimeSection?
)

data class AnimeSection(
    val href: String,
    val otakudesuUrl: String,
    val animeList: List<AnimeItem>
)

data class AnimeItem(
    val title: String,
    val poster: String,
    val episodes: Int,
    val releaseDay: String,
    val latestReleaseDate: String,
    val lastReleaseDate: String,
    val score: String,
    val animeId: String,
    val href: String,
    val otakudesuUrl: String
)
