package com.benhagy.gamefinder3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.benhagy.gamefinder3.data.local.converter.TypeConverter
import com.benhagy.gamefinder3.data.local.entity.BookmarkedGameEntity

/*
database implementation object, used in the di layer to instantiate concrete implementation of the
room database
 */

@Database(
    entities = [BookmarkedGameEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class BookmarksDatabase: RoomDatabase() {

    abstract fun bookmarksDao(): BookmarksDao
}