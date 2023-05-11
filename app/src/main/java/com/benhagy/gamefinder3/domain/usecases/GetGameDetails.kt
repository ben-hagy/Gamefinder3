package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.domain.models.GameDetails
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.util.Resource
import javax.inject.Inject


class GetGameDetails @Inject constructor(
    private val repository: GamefinderRepository
) {

    suspend operator fun invoke(id: Int): Resource<GameDetails> {
        return repository.getGameDetails(id)
    }
}