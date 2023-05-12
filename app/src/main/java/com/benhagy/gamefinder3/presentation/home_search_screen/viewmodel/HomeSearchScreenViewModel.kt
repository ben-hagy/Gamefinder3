package com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.domain.usecases.UseCaseContainer
import com.benhagy.gamefinder3.util.Constants.SEARCH_DELAY_TIME
import com.benhagy.gamefinder3.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeSearchScreenViewModel @Inject constructor(
    private val useCaseContainer: UseCaseContainer
) : ViewModel() {

    var state by mutableStateOf(HomeSearchScreenState())

    private var searchJob: Job? = null

    init {
        getGenresList()
        getGamesList()
    }

    fun onEvent(event: HomeSearchScreenEvent) {
        when (event) {
            is HomeSearchScreenEvent.OnSearchQueryChanged -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(SEARCH_DELAY_TIME)
                    getGamesList()
                }
            }
            is HomeSearchScreenEvent.OnGenreButtonClicked -> {
                state = state.copy(genreId = event.genreId, isRefreshing = true)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    filterGamesListByClickedGenre(genreId = event.genreId)
                }
                state = state.copy(isRefreshing = false)
            }
        }
    }

    private fun getGamesList(
        query: String = state.searchQuery.lowercase(), fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            useCaseContainer.getAndSearchGamesList(fetchFromRemote, query).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                state = state.copy(
                                    games = listings, isRefreshing = true
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
        state = state.copy(isRefreshing = false)
    }

    private fun filterGamesListByClickedGenre(
        genreId: String, fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            useCaseContainer.filterGamesByClickedGenre(fetchFromRemote, genreId).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                state = state.copy(
                                    games = listings, isRefreshing = true
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
        state = state.copy(isRefreshing = false)
    }

    private fun getGenresList(fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            useCaseContainer.getGenres(fetchFromRemote).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { listings ->
                            state = state.copy(
                                genres = listings
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