package com.dharma.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dharma.data.mapper.toPokemon
import com.dharma.domain.model.Pokemon
import com.dharma.domain.repository.PokemonRepository
import com.dharma.network.PokemonApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi
) : PokemonRepository {

    override fun getPokemonList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokemonPagingSource(pokemonApi) }
        ).flow
    }

    override suspend fun getPokemonDetails(id: Int): Result<Pokemon> {
        return try {
            val response = pokemonApi.getPokemonDetail(id)
            Result.success(response.toPokemon())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchPokemon(query: String): Result<List<Pokemon>> {
        return try {
            val response = pokemonApi.getPokemonList(limit = 1000, offset = 0)
            val filtered = response.results.filter {
                it.name.contains(query, ignoreCase = true)
            }
            Result.success(filtered.map { it.toPokemon() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}