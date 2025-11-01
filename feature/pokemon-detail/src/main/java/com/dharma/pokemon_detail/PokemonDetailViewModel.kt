package com.dharma.pokemon_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dharma.domain.usecases.GetPokemonDetailUseCase
import com.dharma.pokemon_detail.ui.state.PokemonDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonId: Int = checkNotNull(savedStateHandle["pokemonId"])

    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    init {
        loadPokemonDetail()
    }

    private fun loadPokemonDetail() {
        viewModelScope.launch {
            _uiState.value = PokemonDetailUiState.Loading
            getPokemonDetailUseCase(pokemonId)
                .onSuccess { pokemon ->
                    _uiState.value = PokemonDetailUiState.Success(pokemon)
                }
                .onFailure { error ->
                    _uiState.value = PokemonDetailUiState.Error(error.message ?: "Unknown error")
                }
        }
    }
}