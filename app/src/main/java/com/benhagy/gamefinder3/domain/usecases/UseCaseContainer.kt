package com.benhagy.gamefinder3.domain.usecases

data class UseCaseContainer(
    val getAndSearchGamesList: GetAndSearchGamesList,
    val getGameDetails: GetGameDetails,
    val getGenres: GetGenres,
    val filterGamesByClickedGenre: FilterGamesByClickedGenre,
    val addBookmark: AddBookmark,
    val deleteBookmark: DeleteBookmark,
    val getAllBookmarks: GetAllBookmarks,
    val checkIfGameIsBookmarked: CheckIfGameIsBookmarked,
    val upsertUserNote: UpsertUserNote
)
