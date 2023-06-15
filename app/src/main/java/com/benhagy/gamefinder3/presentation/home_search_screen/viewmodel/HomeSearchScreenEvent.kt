package com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel

// events representing non-navigation actions the user can perform on the home screen

sealed class HomeSearchScreenEvent {
    data class OnSearchQueryChanged(val query: String): HomeSearchScreenEvent()
    data class OnGenreButtonClicked(val genreId: String): HomeSearchScreenEvent()
}
