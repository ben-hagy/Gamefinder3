package com.benhagy.gamefinder3.data.repository

import com.benhagy.gamefinder3.data.local.FavoritesDao
import com.benhagy.gamefinder3.data.local.entity.FavoriteGameEntity
import com.benhagy.gamefinder3.data.remote.GamefinderApi
import com.benhagy.gamefinder3.domain.models.GameDetails
import com.benhagy.gamefinder3.domain.models.Genre
import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GamefinderRepositoryImpl @Inject constructor(
    private val api: GamefinderApi,
    private val dao: FavoritesDao
) : GamefinderRepository {

    override suspend fun getGamesList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<ListedGame>>> {
        return flow {
            emit(Resource.Loading())
            val remoteListings = try {
                api.getGames(search = query)
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
                    data = remoteListings?.results?.map { it.toListedGame() })
            )
        }
    }

    override suspend fun filterGamesListByClickedGenre(
        fetchFromRemote: Boolean,
        genreId: String
    ): Flow<Resource<List<ListedGame>>> {
        return flow {
            emit(Resource.Loading())
            val remoteListings = try {
                api.getGames(genres = genreId)
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
                    data = remoteListings?.results?.map { it.toListedGame() }
                )
            )
        }
    }


    override suspend fun getGenresList(fetchFromRemote: Boolean): Flow<Resource<List<Genre>>> {
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

    override suspend fun getGameDetails(id: Int): Flow<Resource<GameDetails>> = flow {
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

    override suspend fun addGameToFavorites(game: GameDetails) {
        dao.upsertFavorite(game.toFavoriteGameEntity())
    }

    override suspend fun getAllFavorites(): Flow<List<FavoriteGameEntity>> {
        return dao.getAllFavorites()
    }

    override suspend fun removeGameFromFavorites(id: Int) {
        dao.deleteFavorite(id)
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return dao.isFavorite(id)
    }
}