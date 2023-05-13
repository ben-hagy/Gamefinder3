package com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.domain.usecases.UseCaseContainer
import com.benhagy.gamefinder3.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    useCaseContainer: UseCaseContainer
) : ViewModel() {
    var state by mutableStateOf(GameDetailsScreenState())

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<Int>("id") ?: return@launch
            useCaseContainer.getGameDetails(id).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        result.data.let { details ->
                            state = state.copy(
                                gameDetails = details
                            )
                        }
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

}