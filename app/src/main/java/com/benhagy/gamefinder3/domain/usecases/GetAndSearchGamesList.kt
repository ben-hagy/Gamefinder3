package com.benhagy.gamefinder3.domain.usecases

import androidx.paging.PagingData
import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAndSearchGamesList @Inject constructor(
    private val repository: GamefinderRepository
) {

    operator fun invoke(query: String?, genreId: String?): Flow<PagingData<ListedGame>> {
        return repository.getGamesList(query, genreId)
    }
}