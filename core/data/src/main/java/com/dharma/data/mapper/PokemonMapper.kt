package com.dharma.data.mapper

import com.dharma.domain.model.Pokemon
import com.dharma.network.model.PokemonDetailResponse
import com.dharma.network.model.PokemonListResponse
import com.dharma.network.model.PokemonResult

fun PokemonResult.toPokemon(): Pokemon {
    val id = url.split("/").last { it.isNotEmpty() }.toInt()
    return Pokemon(
        id = id,
        name = name,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
        types = emptyList(),
        stats = emptyList(),
        abilities = emptyList(),
        height = 0,
        weight = 0
    )
}

fun PokemonDetailResponse.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        imageUrl = sprites.other?.officialArtwork?.frontDefault ?: "",
        types = types.map { it.type.name },
        stats = stats.map { Pokemon.Stat(it.stat.name, it.baseStat) },
        abilities = abilities.map { it.ability.name },
        height = height,
        weight = weight
    )
}

fun PokemonListResponse.toPokemonList(): List<Pokemon> {
    return results.map { it.toPokemon() }
}
