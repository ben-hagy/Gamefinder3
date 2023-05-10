package com.benhagy.gamefinder3.data.repository

import com.benhagy.gamefinder3.data.remote.GamefinderApi
import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GamfinderRepositoryImpl @Inject constructor(
    private val api: GamefinderApi
) : GamefinderRepository {

    override suspend fun getGamesList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<ListedGame>>> {
        return flow {
            emit(Resource.Loading())
            val remoteListings = try {
                api.getGames()
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
                    data = remoteListings?.map { it.toListedGame() })
            )
        }

    }
}