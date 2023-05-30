package com.benhagy.gamefinder3.di

import android.app.Application
import androidx.room.Room
import com.benhagy.gamefinder3.data.local.BookmarksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): BookmarksDatabase {
        return Room.databaseBuilder(
            application,
            BookmarksDatabase::class.java,
            name = "bookmarks_database"
        )
            .build()
    }


    @Provides
    @Singleton
    fun provideBookmarksDao(bookmarksDatabase: BookmarksDatabase) = bookmarksDatabase.bookmarksDao()


}
