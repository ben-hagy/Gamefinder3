package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import javax.inject.Inject

class CheckIfGameIsBookmarked @Inject constructor(
    private val repository: GamefinderRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        return repository.isBookmarked(id)
    }
}