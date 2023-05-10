package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// this class is commented out because i'm implementing basic screens without usecases first
// i'll need to make all usecases, a container, and di object to provide the container

//class GetGamesList @Inject constructor(
//    private val repository: GamefinderRepository
//) {
//
//    suspend operator fun invoke(): Flow<Resource<List<ListedGame>>> {
//        return repository.getGamesList(false, "")
//    }
//}