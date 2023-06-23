package com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel

import androidx.paging.PagingData
import com.benhagy.gamefinder3.domain.models.Genre
import com.benhagy.gamefinder3.domain.models.ListedGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// state objects that act as containers for data objects that change on the home screen

data class HomeSearchScreenState(
    val games: Flow<PagingData<ListedGame>> = flow { },
    val genres: List<Genre> = emptyList(),
    val contentIsLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val genreId: String? = null,
)
