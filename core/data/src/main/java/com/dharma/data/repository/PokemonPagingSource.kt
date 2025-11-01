package com.dharma.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dharma.data.mapper.toPokemon
import com.dharma.domain.model.Pokemon
import com.dharma.network.PokemonApi

class PokemonPagingSource(
    private val api: PokemonApi
) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val offset = params.key ?: 0
            val response = api.getPokemonList(limit = params.loadSize, offset = offset)
            val pokemons = response.results.map { it.toPokemon() }

            LoadResult.Page(
                data = pokemons,
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = if (pokemons.isEmpty()) null else offset + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            anchorPage?.prevKey?.plus(state.config.pageSize)
                ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }
}
