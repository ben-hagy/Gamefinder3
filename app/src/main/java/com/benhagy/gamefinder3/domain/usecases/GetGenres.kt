package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.domain.models.Genre
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// use case to call a single function via the repo interface, provided via the UseCaseContainer to
// screen viewmodels

class GetGenres @Inject constructor(
    private val repository: GamefinderRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Genre>>> {
        return repository.getGenresList()
    }
}