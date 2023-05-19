package com.benhagy.gamefinder3.presentation.favorites_screen.viewmodel

sealed class FavoritesScreenEvent {
    data class RemoveSelectedFavorite(val id: Int): FavoritesScreenEvent()
}