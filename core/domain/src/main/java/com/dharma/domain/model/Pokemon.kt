package com.dharma.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val stats: List<Stat>,
    val abilities: List<String>,
    val height: Int,
    val weight: Int
){

    data class Stat(
        val name: String,
        val value: Int
    )

}
