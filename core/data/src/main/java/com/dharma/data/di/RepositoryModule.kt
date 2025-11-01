package com.dharma.data.di

import com.dharma.data.repository.PokemonRepositoryImpl
import com.dharma.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/***
 * The module for the repository.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /***
     * Binds the implementation of the repository to the interface.
     */
    @Binds
    @Singleton
    abstract fun bindPokemonRepository(
        pokemonRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository
}