package com.peusoaresf.pokepal.network.dto

data class Sprites(
    val front_default: String
)

data class PokemonDTO(
    val name: String,
    val sprites: Sprites
)