package com.dharma.domain.di

import com.dharma.domain.repository.PokemonRepository
import com.dharma.domain.usecases.GetPokemonDetailUseCase
import com.dharma.domain.usecases.GetPokemonListUseCase
import com.dharma.domain.usecases.SearchPokemonUseCase
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
    fun providesGetPokemonListUseCase(
        pokemonRepository: PokemonRepository
    ): GetPokemonListUseCase =
        GetPokemonListUseCase(pokemonRepository
    )

    @Provides
    @Singleton
    fun providesSearchPokemonUseCase(
        pokemonRepository: PokemonRepository
    ): SearchPokemonUseCase =
        SearchPokemonUseCase(pokemonRepository
    )

    @Provides
    @Singleton
    fun providesGetPokemonDetailUseCase(
        pokemonRepository: PokemonRepository
    ): GetPokemonDetailUseCase =
        GetPokemonDetailUseCase(pokemonRepository
    )
}