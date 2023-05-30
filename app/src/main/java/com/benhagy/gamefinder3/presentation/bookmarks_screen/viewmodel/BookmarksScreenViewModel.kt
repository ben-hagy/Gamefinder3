package com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benhagy.gamefinder3.domain.usecases.UseCaseContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookmarksScreenViewModel @Inject constructor(
    private val useCaseContainer: UseCaseContainer
): ViewModel() {
    var state = mutableStateOf(BookmarksScreenState())

    init {
        viewModelScope.launch {
            getAllBookmarks()
        }
    }

    private fun getAllBookmarks() {
        viewModelScope.launch {
            try {
                state.value = BookmarksScreenState(isLoading = true)
                val result = withContext(Dispatchers.IO) {
                    useCaseContainer.getAllBookmarks()
                }
                result.collect {
                    state.value = BookmarksScreenState(bookmarkedGames = it)
                }
            } catch (e: Exception) {
                state.value =
                    BookmarksScreenState(error = e.message ?: "Error fetching favorite games.")
            }
        }
    }

    fun onEvent(event: BookmarksScreenEvent) {
        when (event) {
            is BookmarksScreenEvent.RemoveSelectedBookmark -> {
                viewModelScope.launch {
                    removedBookmarkedGame(event.id)
                }
            }
            is BookmarksScreenEvent.EditUserNote -> {
                state.value = BookmarksScreenState(userNote = event.note)
                viewModelScope.launch {
                    upsertUserNote(event.note)
                }
            }
        }
    }

    private fun upsertUserNote(note: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseContainer.upsertUserNote(note)
        }
    }

    private fun removedBookmarkedGame(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseContainer.deleteBookmark(id)
        }
    }
}