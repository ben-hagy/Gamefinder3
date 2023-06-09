package com.benhagy.gamefinder3.domain.repository

import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity
import com.benhagy.gamefinder3.domain.models.GameDetails
import com.benhagy.gamefinder3.domain.models.Genre
import com.benhagy.gamefinder3.domain.models.ListedGame
import com.benhagy.gamefinder3.util.Resource
import kotlinx.coroutines.flow.Flow


interface GamefinderRepository {

    suspend fun getGamesList(
        query: String
    ): Flow<Resource<List<ListedGame>>>

    suspend fun filterGamesListByClickedGenre(
        genreId: String
    ): Flow<Resource<List<ListedGame>>>

    suspend fun getGenresList(): Flow<Resource<List<Genre>>>

    suspend fun getGameDetailsWithScreenshots(id: Int): Flow<Resource<GameDetails>>

    suspend fun bookmarkGame(game: GameDetails)

    suspend fun getAllBookmarks(): Flow<List<BookmarkedGameEntity>>

    suspend fun removeBookmarkedGame(id: Int)

    suspend fun isBookmarked(id: Int): Boolean

    suspend fun upsertUserNote(note: String, id: Int)

    suspend fun upsertUserRating(rating: Float, id: Int)

}