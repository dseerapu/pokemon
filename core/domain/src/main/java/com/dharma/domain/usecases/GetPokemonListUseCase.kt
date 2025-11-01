package com.dharma.domain.usecases

import com.dharma.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke() =
        pokemonRepository.getPokemonList()
}