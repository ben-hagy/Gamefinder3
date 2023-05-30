package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.domain.models.GameDetails
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import javax.inject.Inject

class AddBookmark @Inject constructor(
    private val repository: GamefinderRepository
) {
    suspend operator fun invoke(game: GameDetails) {
        return repository.bookmarkGame(game)
    }
}