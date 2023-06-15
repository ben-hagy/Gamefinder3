package com.benhagy.gamefinder3.data.remote.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.benhagy.gamefinder3.data.remote.GamefinderApi
import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.util.Constants.PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
this paging source handles our primary search api call for populating the paginated list of results
on the home/search screen. we can pass a search query and/or a genreId to filter our search results
as per the api queries.

nextKey only goes up to 5, thus ensuring we only load 100 total items per search query (api will
return many more results if we don't limit it)
 */

class GameListPagingSource @Inject constructor(
    private val api: GamefinderApi,
    private val query: String?,
    private val genreId: String?
) : PagingSource<Int, ListedGame>() {
    override fun getRefreshKey(state: PagingState<Int, ListedGame>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListedGame> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = withContext(Dispatchers.IO) {
                api.getGames(
                    page = nextPageNumber,
                    pageSize = PAGE_SIZE,
                    search = query,
                    genres = genreId
                )
            }
            val games: List<ListedGame> = response.results.map { it.toListedGame() }
            LoadResult.Page(
                data = games,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (games.isEmpty()) {
                    null
                } else if (nextPageNumber != 6) {
                    nextPageNumber + 1
                } else {
                    null
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}