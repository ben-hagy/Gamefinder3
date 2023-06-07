package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import javax.inject.Inject

class UpsertUserRating @Inject constructor(
    private val repository: GamefinderRepository
) {
    suspend operator fun invoke(rating: Float, id: Int) {
        return repository.upsertUserRating(rating, id)
    }
}