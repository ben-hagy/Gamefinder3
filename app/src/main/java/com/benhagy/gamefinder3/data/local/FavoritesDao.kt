package com.benhagy.gamefinder3.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.benhagy.gamefinder3.data.local.entity.FavoriteGameEntity
import kotlinx.coroutines.flow.Flow

/*
dao contains functions to interact with our db; namely, saving, deleting, fetching all saved items,
and checking if an item is currently in the db
 */

@Dao
interface FavoritesDao {

    @Upsert
    fun upsertFavorite(game: FavoriteGameEntity)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteGameEntity>>

    @Query("DELETE FROM favorites WHERE id = :id")
    fun deleteFavorite(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE id = :id)")
    fun isFavorite(id: Int): Boolean

}