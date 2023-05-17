package com.benhagy.gamefinder3.presentation.favorites_screen.viewmodel

sealed class FavoritesScreenEvent {
    object GetAllFavorites: FavoritesScreenEvent()
}