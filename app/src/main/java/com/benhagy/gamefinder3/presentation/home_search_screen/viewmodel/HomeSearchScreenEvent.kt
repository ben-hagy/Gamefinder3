package com.benhagy.gamefinder3.presentation.home_search_screen.viewmodel

sealed class HomeSearchScreenEvent {
    data class OnSearchQueryChanged(val query: String): HomeSearchScreenEvent()
}
