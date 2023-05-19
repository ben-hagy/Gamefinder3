package com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benhagy.gamefinder3.domain.models.GameDetails
import com.benhagy.gamefinder3.domain.usecases.UseCaseContainer
import com.benhagy.gamefinder3.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCaseContainer: UseCaseContainer
) : ViewModel() {
    var state by mutableStateOf(GameDetailsScreenState())

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<Int>("id") ?: return@launch
            useCaseContainer.getGameDetails(id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.let { details ->
                            state = state.copy(
                                gameDetails = details,
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

    fun onEvent(event: GameDetailsScreenEvent) {
        when (event) {
            is GameDetailsScreenEvent.SaveGameAsFavorite -> {
                addToFavorites(event.game)

            }

            is GameDetailsScreenEvent.RemoveGameFromFavorites -> {
                isFavorite(event.id)
            }
        }
    }


    fun isFavorite(gameId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCaseContainer.checkIfGameIsFavorite(id = gameId)
            state = state.copy(isFavorite = result)
        }
    }


    private fun addToFavorites(gameDetail: GameDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseContainer.addFavorite(gameDetail)
            state = state.copy(isFavorite = true)
        }
    }

    private fun removeFromFavorites(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseContainer.deleteFavorite(id)
            state = state.copy(isFavorite = false)
        }
    }

}