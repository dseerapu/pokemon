package com.dharma.domain.usecases

import androidx.paging.PagingData
import com.dharma.domain.model.Pokemon
import com.dharma.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    suspend operator fun invoke(query: String) =
        pokemonRepository.searchPokemon(query)
}