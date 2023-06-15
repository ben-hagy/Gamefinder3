package com.benhagy.gamefinder3.util

/*
these values are used throughout the app and never change.
"SEARCH_DELAY_TIME" is also used to artifically delay scroll to the top of the home screen list when
query or genreId parameters change, for smoother UX
"DELETE_DELAY_TIME" is similarly used to improve UX feedback when users delete bookmarked items
 */

object Constants {

    const val BASE_URL = "https://api.rawg.io/api/"
    const val API_KEY = "a1d8ecfafc664b4a8e809c0c96bd1dab"
    const val SEARCH_DELAY_TIME = 500L
    const val DELETE_DELAY_TIME = 250L
    const val PAGE_SIZE = 20
}