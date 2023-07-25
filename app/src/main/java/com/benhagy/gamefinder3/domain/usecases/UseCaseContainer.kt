package com.benhagy.gamefinder3.domain.usecases

// container provided to our view-models to perform data layer repo actions

data class UseCaseContainer(
    val getAndSearchGamesList: GetAndSearchGamesList,
    val getGameDetails: GetGameDetails,
    val getGenres: GetGenres,
    val addBookmark: AddBookmark,
    val deleteBookmark: DeleteBookmark,
    val getAllBookmarks: GetAllBookmarks,
    val checkIfGameIsBookmarked: CheckIfGameIsBookmarked,
    val upsertUserNote: UpsertUserNote,
    val upsertUserRating: UpsertUserRating
)