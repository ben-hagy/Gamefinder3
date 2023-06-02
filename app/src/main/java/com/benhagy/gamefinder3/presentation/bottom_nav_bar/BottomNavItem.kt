package com.benhagy.gamefinder3.presentation.bottom_nav_bar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.presentation.destinations.BookmarksScreenDestination
import com.benhagy.gamefinder3.presentation.destinations.DirectionDestination
import com.benhagy.gamefinder3.presentation.destinations.HomeSearchScreenDestination

enum class BottomNavItem(
    val direction: DirectionDestination,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    HomeSearchScreen(HomeSearchScreenDestination, Icons.Default.Search, R.string.game_search_bottom_nav_title),
    BookmarksScreen(BookmarksScreenDestination, Icons.Default.BookmarkBorder, R.string.bookmarked_games_bottom_nav_title)
}