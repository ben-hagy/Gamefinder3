package com.benhagy.gamefinder3.presentation.favorites_screen.viewmodel

import com.benhagy.gamefinder3.data.local.entity.FavoriteGameEntity

data class FavoritesScreenState(
    val favoriteGames: List<FavoriteGameEntity> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false,
    val deleteButtonIsClicked: Boolean = false,
)
