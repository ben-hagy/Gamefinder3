package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.domain.models.GameDetails
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// use case to call a single function via the repo interface, provided via the UseCaseContainer to
// screen viewmodels

class GetGameDetails @Inject constructor(
    private val repository: GamefinderRepository
) {

    suspend operator fun invoke(id: Int): Flow<Resource<GameDetails>> {
        return repository.getGameDetailsWithScreenshots(id)
    }
}