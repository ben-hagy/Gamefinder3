package com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
) : ViewModel() {

    var state by mutableStateOf(BookmarksScreenState())

    init {
        getAllBookmarks()
    }

    private fun getAllBookmarks() {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true)
                val result = withContext(Dispatchers.IO) {
                    useCaseContainer.getAllBookmarks()
                }
                result.collect {
                    state = state.copy(
                        // sort ensures the games are listed in the order they were added
                        bookmarkedGames = it.sortedBy {
                                bookmarkedGameEntity -> bookmarkedGameEntity.dateAdded
                        }
                    )
                }
            } catch (e: Exception) {
                state = state.copy(error = e.message)
            }
        }
    }

    fun onEvent(event: BookmarksScreenEvent) {
        when (event) {
            is BookmarksScreenEvent.RemoveSelectedBookmark -> {
                viewModelScope.launch {
                    removeBookmarkedGame(event.id)
                }
            }

            is BookmarksScreenEvent.EditUserNote -> {
                state = state.copy(userNote = event.note, bookmarkedId = event.id)
                viewModelScope.launch {
                    upsertUserNote(event.note, event.id)
                }
            }

            is BookmarksScreenEvent.EditUserRating -> {
                state = state.copy(userRating = event.rating, bookmarkedId = event.id)
                viewModelScope.launch {
                    upsertUserRating(event.rating, event.id)
                }
            }
        }
    }

    private fun removeBookmarkedGame(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseContainer.deleteBookmark(id)
        }
    }

    private fun upsertUserNote(note: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseContainer.upsertUserNote(note, id)
        }
    }

    private fun upsertUserRating(rating: Double, id: Int) {
        viewModelScope.launch {
            useCaseContainer.upsertUserRating(rating, id)
        }
    }
}