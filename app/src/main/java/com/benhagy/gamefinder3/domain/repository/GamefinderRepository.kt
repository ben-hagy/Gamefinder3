package com.benhagy.gamefinder3.domain.repository

import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.util.Resource
import kotlinx.coroutines.flow.Flow

interface GamefinderRepository {

    suspend fun getGamesList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<ListedGame>>>
}