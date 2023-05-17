package com.benhagy.gamefinder3.di

import com.benhagy.gamefinder3.data.local.FavoritesDao
import com.benhagy.gamefinder3.data.remote.GamefinderApi
import com.benhagy.gamefinder3.data.repository.GamefinderRepositoryImpl
import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGamefinderRepository(api: GamefinderApi, dao: FavoritesDao): GamefinderRepository {
        return GamefinderRepositoryImpl(api, dao)
    }
}