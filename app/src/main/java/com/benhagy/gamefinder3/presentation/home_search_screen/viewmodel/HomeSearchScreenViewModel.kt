package com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeSearchScreenViewModel @Inject constructor(
    private val repository: GamefinderRepository
) : ViewModel() {

    var state by mutableStateOf(HomeSearchScreenState())

    private var searchJob: Job? = null

    init {
        getGamesList()
    }

    fun onEvent(event: HomeSearchScreenEvent) {
        when (event) {
//            is HomeSearchScreenEvent.Refresh -> {
//                getGamesList(fetchFromRemote = true)
//            }
            is HomeSearchScreenEvent.OnSearchQueryChanged -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getGamesList()
                }
            }
        }
    }

    private fun getGamesList(
        query: String = state.searchQuery.lowercase(), fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository.getGamesList(fetchFromRemote, query).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                state = state.copy(
                                    games = listings
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