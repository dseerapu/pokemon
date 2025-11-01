package com.dharma.pokemon_detail.ui.state

import com.dharma.domain.model.Pokemon

/**
 * A sealed class representing the different states of the Pokemon detail screen.
 */
sealed class PokemonDetailUiState {
    object Loading : PokemonDetailUiState()
    data class Success(val pokemon: Pokemon) : PokemonDetailUiState()
    data class Error(val message: String) : PokemonDetailUiState()
}