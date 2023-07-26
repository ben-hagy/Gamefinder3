package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import javax.inject.Inject

// use case to call a single function via the repo interface, provided via the UseCaseContainer to
// screen viewmodels

class UpsertUserNote @Inject constructor(
    private val repository: GamefinderRepository
) {
    suspend operator fun invoke(note: String, id: Int) {
        return repository.upsertUserNote(note, id)
    }
}