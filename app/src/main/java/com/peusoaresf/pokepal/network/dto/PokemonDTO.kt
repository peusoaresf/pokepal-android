package com.peusoaresf.pokepal.network.dto

import com.peusoaresf.pokepal.database.entity.PokemonEntity
import com.peusoaresf.pokepal.domain.Pokemon

data class Sprites(
    val front_default: String?
)

data class PokemonDTO(
    val id: Int,
    val name: String,
    val sprites: Sprites
)

fun List<PokemonDTO>.asDatabaseModel(): List<PokemonEntity> {
    return map { pokemonDTO ->
        PokemonEntity(
            id = pokemonDTO.id,
            name = pokemonDTO.name,
            spriteUrl = pokemonDTO.sprites.front_default ?: ""
        )
    }
}