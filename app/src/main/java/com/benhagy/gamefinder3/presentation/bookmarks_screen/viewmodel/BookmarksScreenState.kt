package com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel

import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity

data class BookmarksScreenState(
    val bookmarkedGames: List<BookmarkedGameEntity> = emptyList(),
    val userNote: String = "",
    val error: String = "",
    val isLoading: Boolean = false,
    val isBookmarked: Boolean = false,
    val bookmarkedId: Int? = null
)

