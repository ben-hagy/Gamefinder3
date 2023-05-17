package com.benhagy.gamefinder3.domain.usecases

data class UseCaseContainer(
    val getAndSearchGamesList: GetAndSearchGamesList,
    val getGameDetails: GetGameDetails,
    val getGenres: GetGenres,
    val filterGamesByClickedGenre: FilterGamesByClickedGenre,
    val addFavorite: AddFavorite,
    val deleteFavorite: DeleteFavorite,
    val getAllFavorites: GetAllFavorites,
    val checkIfGameIsFavorite: CheckIfGameIsFavorite
)
