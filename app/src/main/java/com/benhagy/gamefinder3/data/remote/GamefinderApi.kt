package com.benhagy.gamefinder3.data.remote

import com.benhagy.gamefinder3.data.remote.responses.GameDetailsResponse
import com.benhagy.gamefinder3.data.remote.responses.GameScreenshotsResponse
import com.benhagy.gamefinder3.data.remote.responses.GamesListResponse
import com.benhagy.gamefinder3.data.remote.responses.GenresResponse
import com.benhagy.gamefinder3.data.remote.responses.ListedGameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamefinderApi {

    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("search") search: String? = null,
        @Query("parent_platforms") parentPlatforms: String? = null,
        @Query("genres") genres: String? = null,
        @Query("platforms") platforms: String? = null,
        @Query("stores") stores: String? = null,
        @Query("developers") developers: String? = null,
        @Query("publishers") publishers: String? = null,
        @Query("tags") tags: String? = null,
        @Query("dates") dates: String? = null,
        @Query("ordering") ordering: String? = null,
    ): GamesListResponse

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") id: Int,
    ): GameDetailsResponse

    @GET("genres")
    suspend fun getGenres(
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("ordering") ordering: String? = null,
    ): GenresResponse

    @GET("games/{id}/screenshots")
    suspend fun getGameScreenshots(
        @Path("id") id: Int,
    ): GameScreenshotsResponse

}