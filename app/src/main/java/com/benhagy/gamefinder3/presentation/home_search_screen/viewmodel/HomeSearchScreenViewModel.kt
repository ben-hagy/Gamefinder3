package com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.domain.usecases.UseCaseContainer
import com.benhagy.gamefinder3.util.Constants.SEARCH_DELAY_TIME
import com.benhagy.gamefinder3.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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

        state = state.copy(
            games = getGamesList(query = state.searchQuery)
        )
    }

    fun onEvent(event: HomeSearchScreenEvent) {
        when (event) {
            is HomeSearchScreenEvent.OnSearchQueryChanged -> {
                state = state.copy(searchQuery = event.query, isSelected = false)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(SEARCH_DELAY_TIME)
                    state = state.copy(
                        games = getGamesList(query = event.query)
                    )
                }
            }

//            is HomeSearchScreenEvent.OnGenreButtonClicked -> {
//                state = state.copy(genreId = event.genreId, isRefreshing = true, isSelected = true)
//                searchJob?.cancel()
//                searchJob = viewModelScope.launch {
//                    filterGamesListByClickedGenre(genreId = event.genreId)
//                }
//                state = state.copy(isRefreshing = false)
//            }

            is HomeSearchScreenEvent.OnSearchClearClicked -> {
                state = state.copy(searchQuery = "", isSelected = false)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    state = state.copy(
                        getGamesList(query = state.searchQuery)
                    )
                }
            }
            else -> Unit
        }
    }

//    private fun getGamesList(
//        query: String = state.searchQuery.lowercase()
//    ) {
//        viewModelScope.launch {
//            useCaseContainer.getAndSearchGamesList(query).collect { result ->
//                when (result) {
//                    is Resource.Success -> {
//                        result.data?.let { listings ->
//                            state = state.copy(
//                                games = listings, isRefreshing = true
//                            )
//                        }
//                    }
//
//                    is Resource.Error ->
//                        state = state.copy(error = "An error occurred!")
//
//                    is Resource.Loading -> {
//                        state = state.copy(isLoading = result.isLoading)
//                    }
//                }
//            }
//        }
//        state = state.copy(isRefreshing = false)
//    }

    private fun getGamesList(query: String): Flow<PagingData<ListedGame>> {
        return useCaseContainer.getAndSearchGamesList(query)
            .cachedIn(viewModelScope)
    }

//    private fun filterGamesListByClickedGenre(
//        genreId: String
//    ) {
//        viewModelScope.launch {
//            useCaseContainer.filterGamesByClickedGenre(genreId).collect { result ->
//                when (result) {
//                    is Resource.Success -> {
//                        result.data?.let { listings ->
//                            state = state.copy(
//                                games = listings, isRefreshing = true
//                            )
//                        }
//                    }
//
//                    is Resource.Error ->
//                        state = state.copy(error = "An error occurred!")
//
//                    is Resource.Loading -> {
//                        state = state.copy(isLoading = result.isLoading)
//                    }
//                }
//            }
//        }
//        state = state.copy(isRefreshing = false)
//    }

    private fun getGenresList() {
        viewModelScope.launch {
            useCaseContainer.getGenres().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { listings ->
                            state = state.copy(
                                genres = listings
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

}