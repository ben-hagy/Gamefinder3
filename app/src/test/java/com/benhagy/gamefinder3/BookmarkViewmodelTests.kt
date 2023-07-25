package com.benhagy.gamefinder3

import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.domain.usecases.AddBookmark
import com.benhagy.gamefinder3.domain.usecases.CheckIfGameIsBookmarked
import com.benhagy.gamefinder3.domain.usecases.DeleteBookmark
import com.benhagy.gamefinder3.domain.usecases.GetAllBookmarks
import com.benhagy.gamefinder3.domain.usecases.GetAndSearchGamesList
import com.benhagy.gamefinder3.domain.usecases.GetGameDetails
import com.benhagy.gamefinder3.domain.usecases.GetGenres
import com.benhagy.gamefinder3.domain.usecases.UpsertUserNote
import com.benhagy.gamefinder3.domain.usecases.UpsertUserRating
import com.benhagy.gamefinder3.domain.usecases.UseCaseContainer
import com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel.BookmarksScreenEvent
import com.benhagy.gamefinder3.presentation.bookmarks_screen.viewmodel.BookmarksScreenViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


class BookmarkViewmodelTests {

    // import of class to test suspend functions
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun verifyIntegrationOfUseCaseContainer() = runTest {
        val testId = 1
        val useCaseContainer = mock<UseCaseContainer>()
        val bookmarksViewModel = BookmarksScreenViewModel(useCaseContainer)

        // change the passed id to testId for test to pass
        bookmarksViewModel.onEvent(BookmarksScreenEvent.RemoveSelectedBookmark(id = 11))
        verify(useCaseContainer).deleteBookmark(testId)
    }

    @Test
    fun verifyIntegrationOfRepoInterface() = runTest {
        val testId = 1
        val repository = mock<GamefinderRepository>()

        val useCaseContainer = UseCaseContainer(
                getAndSearchGamesList = GetAndSearchGamesList(repository),
                getGameDetails = GetGameDetails(repository),
                getGenres = GetGenres(repository),
                addBookmark = AddBookmark(repository),
                deleteBookmark = DeleteBookmark(repository),
                getAllBookmarks = GetAllBookmarks(repository),
                checkIfGameIsBookmarked = CheckIfGameIsBookmarked(repository),
                upsertUserNote = UpsertUserNote(repository),
                upsertUserRating = UpsertUserRating(repository)
            )

        useCaseContainer.deleteBookmark(id = 159)
        verify(repository).removeBookmarkedGame(testId)
    }
}