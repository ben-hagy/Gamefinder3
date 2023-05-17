package com.benhagy.gamefinder3.presentation.favorites_screen.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benhagy.gamefinder3.domain.usecases.UseCaseContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val useCaseContainer: UseCaseContainer
): ViewModel() {
    var state = mutableStateOf(FavoritesScreenState())

    init {
        viewModelScope.launch {
            getAllFavorites()
        }
    }

    private fun getAllFavorites() {
        viewModelScope.launch {
            try {
                state.value = FavoritesScreenState(isLoading = true)
                val result = withContext(Dispatchers.IO) {
                    useCaseContainer.getAllFavorites()
                }
                result.collect {
                    state.value = FavoritesScreenState(favoriteGames = it)
                }
            } catch (e: Exception) {
                state.value = FavoritesScreenState(error = e.message ?: "Error fetching favorite games.")
            }

        }
    }
}