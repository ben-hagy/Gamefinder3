package com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel

sealed class GameDetailsScreenEvent {
    data class SaveGameAsFavorite(val id: Int): GameDetailsScreenEvent()
    data class RemoveGameFromFavorites(val id: Int): GameDetailsScreenEvent()
}
