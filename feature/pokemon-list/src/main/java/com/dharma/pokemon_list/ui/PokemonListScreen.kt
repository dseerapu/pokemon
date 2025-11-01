package com.dharma.pokemon_list.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dharma.pokemon_list.ui.state.PokemonListUiState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dharma.pokemon_list.viewmodel.PokemonListViewModel

/***
 * A composable that displays the Pokemon list screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    onPokemonClick: (Int) -> Unit,
    viewModel: PokemonListViewModel = hiltViewModel<PokemonListViewModel>()
) {

    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val pagedPokemons = viewModel.pagedPokemons.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokemon") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            PokemonSearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
            )

            when (val state = uiState) {
                is PokemonListUiState.Loading -> {
                    if (searchQuery.isNotEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else {
                        LazyColumn {
                            items(pagedPokemons.itemCount) { index ->
                                pagedPokemons[index]?.let { pokemon ->
                                    PokemonListItem(
                                        pokemon = pokemon,
                                        onClick = { onPokemonClick(pokemon.id) }
                                    )
                                }
                            }
                        }
                    }
                }

                is PokemonListUiState.Success -> {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.pokemon) { pokemon ->
                            PokemonListItem(
                                pokemon = pokemon,
                                onClick = { onPokemonClick(pokemon.id) }
                            )
                        }
                    }
                }

                is PokemonListUiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${state.message}")
                    }
                }
            }
        }
    }
}
