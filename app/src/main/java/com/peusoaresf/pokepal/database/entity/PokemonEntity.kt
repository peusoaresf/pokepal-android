package com.peusoaresf.pokepal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.peusoaresf.pokepal.domain.Pokemon

@Entity(tableName = "table_pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val spriteUrl: String
)

fun List<PokemonEntity>.asDomainModel(): List<Pokemon> {
    return map { pokemonEntity ->
        Pokemon(
            id = pokemonEntity.id,
            name = pokemonEntity.name,
            spriteUrl = pokemonEntity.spriteUrl
        )
    }
}