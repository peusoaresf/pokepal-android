package com.peusoaresf.pokepal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.peusoaresf.pokepal.domain.Pokemon
import com.peusoaresf.pokepal.domain.PokemonStats
import com.peusoaresf.pokepal.domain.PokemonType
import com.peusoaresf.pokepal.domain.Stat


@Entity(tableName = "table_pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val image_url: String,
    val is_default: Boolean,
    val primary_type: String,
    val secondary_type: String?
)

fun List<PokemonEntity>.asDomainModel(): List<Pokemon> {
    return map { pokemonEntity ->
        Pokemon(
            id = pokemonEntity.id,
            name = pokemonEntity.name,
            imageUrl = pokemonEntity.image_url,
            primaryType = PokemonType.fromString(pokemonEntity.primary_type),
            secondaryType = if (pokemonEntity.secondary_type != null) PokemonType.fromString(pokemonEntity.secondary_type) else null,
            // TODO: remove mock
            height = 7,
            weight = 69,
            baseStats = PokemonStats(
                hp = Stat(45, 0),
                attack = Stat(49, 0),
                defense = Stat(49, 0),
                specialAttack = Stat(65, 1),
                specialDefense = Stat(65, 0),
                speed = Stat(45, 0),
            )
        )
    }
}