package com.peusoaresf.pokepal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.peusoaresf.pokepal.domain.Pokemon
import com.peusoaresf.pokepal.domain.PokemonStats
import com.peusoaresf.pokepal.domain.PokemonType
import com.peusoaresf.pokepal.domain.Stat


@Entity(tableName = "table_pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val image_url: String,
    val is_default: Boolean,
    val primary_type: String,
    val secondary_type: String?,
    val base_hp: Int,
    val base_hp_effort: Int,
    val base_attack: Int,
    val base_attack_effort: Int,
    val base_defense: Int,
    val base_defense_effort: Int,
    val base_sp_atk: Int,
    val base_sp_atk_effort: Int,
    val base_sp_def: Int,
    val base_sp_def_effort: Int,
    val base_speed: Int,
    val base_speed_effort: Int,
)

fun List<PokemonEntity>.asDomainModel(): List<Pokemon> {
    return map { pokemonEntity ->
        Pokemon(
            id = pokemonEntity.id,
            name = pokemonEntity.name,
            imageUrl = pokemonEntity.image_url,
            primaryType = PokemonType.fromString(pokemonEntity.primary_type),
            secondaryType = if (pokemonEntity.secondary_type != null) PokemonType.fromString(pokemonEntity.secondary_type) else null,
            height = pokemonEntity.height,
            weight = pokemonEntity.weight,
            baseStats = PokemonStats(
                hp = Stat(pokemonEntity.base_hp, pokemonEntity.base_hp_effort),
                attack = Stat(pokemonEntity.base_attack, pokemonEntity.base_attack_effort),
                defense = Stat(pokemonEntity.base_defense, pokemonEntity.base_defense_effort),
                specialAttack = Stat(pokemonEntity.base_sp_atk, pokemonEntity.base_sp_atk_effort),
                specialDefense = Stat(pokemonEntity.base_sp_def, pokemonEntity.base_sp_def_effort),
                speed = Stat(pokemonEntity.base_speed, pokemonEntity.base_speed_effort),
            )
        )
    }
}