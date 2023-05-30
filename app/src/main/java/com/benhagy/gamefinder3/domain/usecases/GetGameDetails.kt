package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.domain.models.GameDetails
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetGameDetails @Inject constructor(
    private val repository: GamefinderRepository
) {

    suspend operator fun invoke(id: Int): Flow<Resource<GameDetails>> {
        return repository.getGameDetailsWithScreenshots(id)
    }
}