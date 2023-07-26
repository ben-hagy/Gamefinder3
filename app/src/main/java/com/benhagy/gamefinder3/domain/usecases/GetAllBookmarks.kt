package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// use case to call a single function via the repo interface, provided via the UseCaseContainer to
// screen viewmodels

class GetAllBookmarks @Inject constructor(
    private val repository: GamefinderRepository
) {
    suspend operator fun invoke(): Flow<List<BookmarkedGameEntity>> {
        return repository.getAllBookmarks()
    }
}