package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import javax.inject.Inject

class DeleteBookmark @Inject constructor(
    private val repository: GamefinderRepository
){
    suspend operator fun invoke(id: Int) {
        return repository.removeBookmarkedGame(id)
    }
}