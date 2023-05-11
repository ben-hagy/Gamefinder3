package com.benhagy.gamefinder3.di

import com.benhagy.gamefinder3.domain.repository.GamefinderRepository
import com.benhagy.gamefinder3.domain.usecases.GetAndSearchGamesList
import com.benhagy.gamefinder3.domain.usecases.GetGameDetails
import com.benhagy.gamefinder3.domain.usecases.GetGenres
import com.benhagy.gamefinder3.domain.usecases.UseCaseContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCaseContainer(repository: GamefinderRepository): UseCaseContainer {
        return UseCaseContainer(
            getAndSearchGamesList = GetAndSearchGamesList(repository),
            getGameDetails = GetGameDetails(repository),
            getGenres = GetGenres(repository)
        )
    }
}