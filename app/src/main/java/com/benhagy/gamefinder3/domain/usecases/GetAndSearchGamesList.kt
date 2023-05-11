package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAndSearchGamesList @Inject constructor(
    private val repository: GamefinderRepository
) {

    suspend operator fun invoke(fetchFromRemote: Boolean, query: String): Flow<Resource<List<ListedGame>>> {
        return repository.getGamesList(false, query)
    }
}