package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.domain.models.Genre
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenres @Inject constructor(
    private val repository: GamefinderRepository
) {
    suspend operator fun invoke(fetchFromRemote: Boolean): Flow<Resource<List<Genre>>> {
        return repository.getGenresList(fetchFromRemote)
    }
}