package com.benhagy.gamefinder3.data.remote.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.benhagy.gamefinder3.data.remote.GamefinderApi
import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.util.Constants.PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameListPagingSource @Inject constructor(
    private val api: GamefinderApi,
    private val query: String
) : PagingSource<Int, ListedGame>() {
    override fun getRefreshKey(state: PagingState<Int, ListedGame>): Int? {
        return null
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListedGame> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = withContext(Dispatchers.IO) {
                api.getGames(
                    page = nextPageNumber,
                    pageSize = PAGE_SIZE,
                    search = query
                )
            }
            val games: List<ListedGame> = response.results.map { it.toListedGame() }
            LoadResult.Page(
                data = games,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (games.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}