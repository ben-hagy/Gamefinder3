package com.benhagy.gamefinder3.presentation.favorites_screen.viewmodel

import com.benhagy.gamefinder3.domain.models.GameDetails

data class FavoritesScreenState(
    val favoriteGames: List<GameDetails> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)
