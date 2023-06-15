package com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel

import com.benhagy.gamefinder3.domain.models.GameDetails

// events representing non-navigation actions the user can perform on the details screen

sealed class GameDetailsScreenEvent {
    data class BookmarkGame(val game: GameDetails): GameDetailsScreenEvent()
    data class RemoveGameFromBookmarks(val id: Int): GameDetailsScreenEvent()
}
