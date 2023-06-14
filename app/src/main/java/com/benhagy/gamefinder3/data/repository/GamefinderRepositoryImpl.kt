package com.benhagy.gamefinder3.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.benhagy.gamefinder3.data.local.BookmarksDao
import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity
import com.benhagy.gamefinder3.data.remote.GamefinderApi
import com.benhagy.gamefinder3.data.remote.paging_source.GameListPagingSource
import com.benhagy.gamefinder3.domain.models.GameDetails
import com.benhagy.gamefinder3.domain.models.Genre
import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.util.Constants.PAGE_SIZE
import com.benhagy.gamefinder3.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GamefinderRepositoryImpl @Inject constructor(
    private val api: GamefinderApi,
    private val dao: BookmarksDao
) : GamefinderRepository {

    // api calls:


    // this function returns paginated results for the home screen games list
    override fun getGamesList(query: String?, genreId: String?): Flow<PagingData<ListedGame>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                GameListPagingSource(api, query, genreId)
            }
        ).flow
    }

    // returns stable list of genres for the genre filter buttons
    override suspend fun getGenresList(): Flow<Resource<List<Genre>>> {
        return flow {
            emit(Resource.Loading())
            val remoteGenreListings = try {
                api.getGenres()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data - IO exception!"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data - Http error!"))
                null
            }

            emit(
                Resource.Success(
                    data = remoteGenreListings?.results?.map { it.toGenre() })
            )
        }
    }

    // joins a screenshot response for a given game with a game details response to return for details screen
    override suspend fun getGameDetailsWithScreenshots(id: Int): Flow<Resource<GameDetails>> = flow {
        emit(Resource.Loading())
        val remoteGameDetails = try {
            api.getGameDetails(id)
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error("Couldn't load data - IO exception!"))
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("Couldn't load data - Http error!"))
            null
        }

        val remoteScreenshots = try {
            api.getGameScreenshots(id)
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error("Couldn't load data - IO exception!"))
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("Couldn't load data - Http error!"))
            null
        }
        val screenshots = remoteScreenshots?.results?.map { it.toScreenshot() }
        var game = remoteGameDetails?.toGameDetails()
        game = screenshots?.let { game?.copy(screenshots = it) }

        emit(
            Resource.Success(
                data = game
            )
        )
    }

    // database calls:

    override suspend fun bookmarkGame(game: GameDetails) {
        dao.upsertBookmark(game.toBookmarkedGameEntity())
    }

    override suspend fun getAllBookmarks(): Flow<List<BookmarkedGameEntity>> {
        return dao.getAllBookmarks()
    }

    override suspend fun removeBookmarkedGame(id: Int) {
        dao.deleteBookmark(id)
    }

    override suspend fun isBookmarked(id: Int): Boolean {
        return dao.isBookmark(id)
    }

    override suspend fun upsertUserNote(note: String, id: Int) {
        dao.upsertUserNote(note, id)
    }

    override suspend fun upsertUserRating(rating: Float, id: Int) {
        dao.upsertUserRating(rating, id)
    }
}