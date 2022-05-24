package ru.maxpek.placesoftravel.activity.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface MarkerRepositoryModule {
    @Binds
    @Singleton
    fun bindMarkerRepository(impl: MarkerRepositoryImpl): MarkerRepository
}