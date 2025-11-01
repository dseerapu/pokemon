package com.dharma.domain.usecases

import com.dharma.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    suspend operator fun invoke(id: Int) = pokemonRepository.getPokemonDetails(id)
}