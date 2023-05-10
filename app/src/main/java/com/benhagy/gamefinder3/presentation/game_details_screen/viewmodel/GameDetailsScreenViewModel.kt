package com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: GamefinderRepository
) : ViewModel() {
    var state by mutableStateOf(GameDetailsScreenState())

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<Int>("id") ?: return@launch
            state = state.copy(isLoading = true)
            val gameDetailsResult = async { repository.getGameDetails(id) }
            when (val result = gameDetailsResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        gameDetails = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        gameDetails = null
                    )
                }

                else -> Unit
            }

        }
    }

}