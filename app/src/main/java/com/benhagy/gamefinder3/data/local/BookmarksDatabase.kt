package com.benhagy.gamefinder3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity

/*
database implementation object, used in the di layer to instantiate concrete implementation of the
room database
 */

@Database(
    entities = [BookmarkedGameEntity::class],
    version = 2,
    exportSchema = false
)
abstract class BookmarksDatabase: RoomDatabase() {

    abstract fun bookmarksDao(): BookmarksDao
}