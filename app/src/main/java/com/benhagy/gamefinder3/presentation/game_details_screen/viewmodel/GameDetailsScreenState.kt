package com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel

import com.benhagy.gamefinder3.domain.models.GameDetails

data class GameDetailsScreenState(
    val gameDetails: GameDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isFavorite: Boolean = false
)
