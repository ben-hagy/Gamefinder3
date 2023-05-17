package com.benhagy.gamefinder3.di

import android.app.Application
import androidx.room.Room
import com.benhagy.gamefinder3.data.local.FavoritesDatabase
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
    fun provideDatabase(application: Application): FavoritesDatabase {
        return Room.databaseBuilder(
            application,
            FavoritesDatabase::class.java,
            name = "favorites_database"
        )
            .build()
    }


    @Provides
    @Singleton
    fun provideFavoritesDao(favoritesDatabase: FavoritesDatabase) = favoritesDatabase.favoritesDao()


}