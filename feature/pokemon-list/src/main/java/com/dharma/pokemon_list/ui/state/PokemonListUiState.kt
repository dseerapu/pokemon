package com.dharma.pokemon_list.ui.state

import com.dharma.domain.model.Pokemon

/**
 * A sealed class representing the different states of the Pokemon list screen.
 */
sealed class PokemonListUiState{
    object Loading: PokemonListUiState()
    data class Success(val pokemon: List<Pokemon>) : PokemonListUiState()
    data class Error(val message : String): PokemonListUiState()
}