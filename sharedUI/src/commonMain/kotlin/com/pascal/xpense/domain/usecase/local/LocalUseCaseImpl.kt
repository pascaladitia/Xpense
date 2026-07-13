package com.pascal.xpense.domain.usecase.local

import com.pascal.xpense.data.local.entity.FavoritesEntity
import com.pascal.xpense.data.local.repository.LocalRepository
import org.koin.core.annotation.Single

@Single
class LocalUseCaseImpl(
    private val repository: LocalRepository,
) : LocalUseCase {

    override suspend fun insertFavorite(entity: FavoritesEntity) {
        repository.insertFavorite(entity)
    }

    override suspend fun deleteFavorite(entity: FavoritesEntity) {
        repository.deleteFavorite(entity)
    }

    override suspend fun getFavorite(): List<FavoritesEntity>? {
        return repository.getFavorite()
    }

    override suspend fun getFavorite(title: String): Boolean {
        return repository.getFavorite(title)
    }

    override suspend fun clearFavorite() {
        return repository.clearFavorite()
    }
}