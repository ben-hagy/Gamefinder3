package com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel

sealed class BookmarksScreenEvent {
    data class RemoveSelectedBookmark(val id: Int): BookmarksScreenEvent()
    data class EditUserNote(val note: String, val id: Int): BookmarksScreenEvent()
    data class EditUserRating(val rating: Double, val id: Int): BookmarksScreenEvent()
}