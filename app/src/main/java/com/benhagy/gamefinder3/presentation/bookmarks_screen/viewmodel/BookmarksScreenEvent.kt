package com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel

sealed class BookmarksScreenEvent {
    data class RemoveSelectedBookmark(val id: Int): BookmarksScreenEvent()
    data class EditUserNote(val note: String): BookmarksScreenEvent()
}