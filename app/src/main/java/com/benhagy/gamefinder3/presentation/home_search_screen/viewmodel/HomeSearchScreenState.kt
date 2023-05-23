package com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel

import com.benhagy.gamefinder3.domain.models.Genre
import com.benhagy.gamefinder3.domain.models.ListedGame

data class HomeSearchScreenState(
    val games: List<ListedGame> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val genreId: String = "1",
    val isClicked: Boolean = false,
)
