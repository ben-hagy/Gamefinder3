package com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel

sealed class BookmarksScreenEvent {
    data class RemoveSelectedBookmark(val id: Int): BookmarksScreenEvent()
    data class EditUserRating(val rating: Float, val id: Int): BookmarksScreenEvent()
    data class SaveUserNote(val note: String, val id: Int): BookmarksScreenEvent()
    data class ClearUserNote(val note: String, val id: Int): BookmarksScreenEvent()
}