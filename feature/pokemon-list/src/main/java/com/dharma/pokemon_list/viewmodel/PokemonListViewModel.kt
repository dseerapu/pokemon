package com.dharma.pokemon_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dharma.domain.model.Pokemon
import com.dharma.domain.usecases.GetPokemonListUseCase
import com.dharma.domain.usecases.SearchPokemonUseCase
import com.dharma.pokemon_list.ui.state.PokemonListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase
): ViewModel(){

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    val pagedPokemons: Flow<PagingData<Pokemon>> =
        getPokemonListUseCase().cachedIn(viewModelScope)

    private val _searchResults = MutableStateFlow<List<Pokemon>>(emptyList())
    val searchResults: StateFlow<List<Pokemon>> = _searchResults.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _uiState.value = PokemonListUiState.Loading
            return
        }

        viewModelScope.launch {
            _uiState.value = PokemonListUiState.Loading
            val result = searchPokemonUseCase(query)
            result.onSuccess {
                _uiState.value = PokemonListUiState.Success(it)
            }.onFailure {
                _uiState.value = PokemonListUiState.Error(it.message ?: "Unknown error")
            }
        }
    }
}