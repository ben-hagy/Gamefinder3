package com.benhagy.gamefinder3.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity
import kotlinx.coroutines.flow.Flow

/*
the Dao contains functions to interact with our database: saving, deleting, fetching saved items,
checking if an item is currently in the db, and updating user notes/ratings for stored objects
 */

@Dao
interface BookmarksDao {

    @Upsert
    fun upsertBookmark(game: BookmarkedGameEntity)

    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks(): Flow<List<BookmarkedGameEntity>>

    @Query("DELETE FROM bookmarks WHERE id = :id")
    fun deleteBookmark(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM bookmarks WHERE id = :id)")
    fun isBookmark(id: Int): Boolean

    @Query("""
        UPDATE bookmarks
        SET userNote = :note
        WHERE id = :id
        """)
    fun upsertUserNote(note: String, id: Int)

    @Query("""
        UPDATE bookmarks
        SET userRating = :rating
        WHERE id = :id
    """)
    fun upsertUserRating(rating: Float, id: Int)

}