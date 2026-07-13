package com.pascal.xpense.domain.usecase.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pascal.xpense.data.repository.RemoteRepository
import com.pascal.xpense.domain.mapper.toDomain
import com.pascal.xpense.domain.model.AnimeItem

class AnimePagingSource(
    private val repository: RemoteRepository
) : PagingSource<Int, AnimeItem>() {

    override fun getRefreshKey(state: PagingState<Int, AnimeItem>): Int? {
        return state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeItem> {
        return try {
            val page = params.key ?: 5

            val animeList = repository.getAnimeLive(page).data?.animeList?.map {
                it.toDomain()
            }

            LoadResult.Page(
                data = animeList.orEmpty(),
                prevKey = if (page == 5) null else page - 1,
                nextKey = if (animeList.isNullOrEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
