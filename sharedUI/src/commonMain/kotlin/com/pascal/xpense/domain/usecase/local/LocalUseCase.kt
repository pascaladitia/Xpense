package com.pascal.xpense.domain.usecase.local

import com.pascal.xpense.data.local.entity.FavoritesEntity

interface LocalUseCase {
    suspend fun insertFavorite(entity: FavoritesEntity)
    suspend fun deleteFavorite(entity: FavoritesEntity)
    suspend fun getFavorite(): List<FavoritesEntity>?
    suspend fun getFavorite(title: String): Boolean
    suspend fun clearFavorite()
}