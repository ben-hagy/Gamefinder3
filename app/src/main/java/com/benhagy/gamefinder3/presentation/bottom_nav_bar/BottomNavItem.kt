package com.benhagy.gamefinder3.presentation.bottom_nav_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.benhagy.gamefinder3.presentation.destinations.BookmarksScreenDestination
import com.benhagy.gamefinder3.presentation.destinations.DirectionDestination
import com.benhagy.gamefinder3.presentation.destinations.HomeSearchScreenDestination

enum class BottomNavItem(
    val direction: DirectionDestination,
    val icon: ImageVector,
    val label: String
) {
    HomeSearchScreen(HomeSearchScreenDestination, Icons.Default.Search, "Game Search"),
    BookmarksScreen(BookmarksScreenDestination, Icons.Default.BookmarkBorder, "Bookmarked Games")
}