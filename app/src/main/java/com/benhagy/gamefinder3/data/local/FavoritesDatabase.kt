package com.benhagy.gamefinder3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.benhagy.gamefinder3.data.local.entity.FavoriteGameEntity

@Database(
    entities = [FavoriteGameEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavoritesDatabase: RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao
}