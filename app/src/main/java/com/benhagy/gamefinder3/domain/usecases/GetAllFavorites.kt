package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.data.local.entity.FavoriteGameEntity
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavorites @Inject constructor(
    private val repository: GamefinderRepository
) {
    suspend operator fun invoke(): Flow<List<FavoriteGameEntity>> {
        return repository.getAllFavorites()
    }
}