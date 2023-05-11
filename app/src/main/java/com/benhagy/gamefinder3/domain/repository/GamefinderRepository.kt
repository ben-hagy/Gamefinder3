package com.benhagy.gamefinder3.domain.repository

import com.benhagy.gamefinder3.domain.models.GameDetails
import com.benhagy.gamefinder3.domain.models.Genre
import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.util.Resource
import kotlinx.coroutines.flow.Flow


interface GamefinderRepository {

    suspend fun getGamesList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<ListedGame>>>

    suspend fun filterGamesListByClickedGenre(
        fetchFromRemote: Boolean,
        genreId: String
    ): Flow<Resource<List<ListedGame>>>

    suspend fun getGenresList(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Genre>>>

    suspend fun getGameDetails(id: Int): Resource<GameDetails>


}