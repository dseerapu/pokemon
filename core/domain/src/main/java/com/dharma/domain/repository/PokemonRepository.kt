package com.dharma.domain.repository

import androidx.paging.PagingData
import com.dharma.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

/***
 * Repository interface for retrieving a list of [Pokemon].
 */
interface PokemonRepository {
    fun getPokemonList(): Flow<PagingData<Pokemon>>
    suspend fun getPokemonDetails(id: Int): Result<Pokemon>
    suspend fun searchPokemon(query: String): Result<List<Pokemon>>

}