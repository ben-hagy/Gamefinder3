package com.benhagy.gamefinder3.data.remote

import com.benhagy.gamefinder3.data.remote.responses.GameDetailsResponse
import com.benhagy.gamefinder3.data.remote.responses.GameScreenshotsResponse
import com.benhagy.gamefinder3.data.remote.responses.GamesListResponse
import com.benhagy.gamefinder3.data.remote.responses.GenresResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
api interface that contains all the queries we will send to the api source (RAWG.io) to get our data
responses

we use the same api call (getGames()) to handle all of our querying for filtered results on the home
screen, just with different parameters. this is also the only api call whose response is paginated.
 */

interface GamefinderApi {

    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("search") search: String? = null,
        @Query("genres") genres: String? = null,
    ): GamesListResponse

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") id: Int,
    ): GameDetailsResponse

    @GET("genres")
    suspend fun getGenres(): GenresResponse

    @GET("games/{id}/screenshots")
    suspend fun getGameScreenshots(
        @Path("id") id: Int,
    ): GameScreenshotsResponse

}