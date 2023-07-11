package com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel

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

                    is Resource.Error ->
                        state = state.copy(error = "An error occurred!")
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

    fun onEvent(event: GameDetailsScreenEvent) {
        when (event) {
            is GameDetailsScreenEvent.BookmarkGame -> {
                addBookmarkedGame(event.game)

            }

            is GameDetailsScreenEvent.RemoveGameFromBookmarks -> {
                removeBookmarkedGame(event.id)
            }
        }
    }


    fun isBookmarked(gameId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCaseContainer.checkIfGameIsBookmarked(id = gameId)
            state = state.copy(isBookmarked = result)
        }
    }


    private fun addBookmarkedGame(gameDetail: GameDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseContainer.addBookmark(gameDetail)
            state = state.copy(isBookmarked = true)
        }
    }

    private fun removeBookmarkedGame(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseContainer.deleteBookmark(id)
            state = state.copy(isBookmarked = false)
        }
    }

}