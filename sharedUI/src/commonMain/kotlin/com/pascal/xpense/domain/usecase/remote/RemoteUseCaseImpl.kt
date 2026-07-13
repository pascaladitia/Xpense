package com.pascal.xpense.domain.usecase.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pascal.xpense.data.repository.RemoteRepository
import com.pascal.xpense.domain.mapper.toDomain
import com.pascal.xpense.domain.model.Anime
import com.pascal.xpense.domain.model.AnimeItem
import com.pascal.xpense.domain.usecase.pagination.AnimePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class RemoteUseCaseImpl(
    private val repository: RemoteRepository
) : RemoteUseCase {

    override suspend fun getAnimeHome(): Flow<Anime> = flow {
        emit(repository.getAnimeHome().data.toDomain())
    }

    override suspend fun getAnimeLive(): Flow<PagingData<AnimeItem>> {
        return Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = {
                AnimePagingSource(repository)
            }
        ).flow
    }
}
