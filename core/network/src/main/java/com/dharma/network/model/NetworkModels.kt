package com.dharma.network.model

import com.squareup.moshi.Json

data class PokemonListResponse (
    val results: List<PokemonResult>
)

data class PokemonResult (
    val name: String,
    val url: String
)

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<PokemonType>,
    val stats: List<Stat>,
    val abilities: List<PokemonAbility>,
)

data class Sprites(
    @Json(name="front_default")
    val frontDefault: String?,
    val other: OtherSprite?
)

data class OtherSprite(
    @Json(name="official-artwork")
    val officialArtwork: OfficialArtwork?
)

data class OfficialArtwork(
    @Json(name="front_default")
    val frontDefault: String?
)

data class Stat(
    @Json(name="base_stat")
    val baseStat: Int,
    val stat: StatDetail
)

data class StatDetail(
    val name: String
)

data class PokemonType(
    val type: TypeDetail
)

data class TypeDetail(
    val name: String
)

data class PokemonAbility(
    val ability: AbilityDetail
)

data class AbilityDetail(
    val name: String
)