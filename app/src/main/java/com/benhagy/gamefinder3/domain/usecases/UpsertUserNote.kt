package com.benhagy.gamefinder3.domain.usecases

import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import javax.inject.Inject

class UpsertUserNote @Inject constructor(
    private val repository: GamefinderRepository
) {
    suspend operator fun invoke(note: String) {
        return repository.upsertUserNote(note)
    }
}