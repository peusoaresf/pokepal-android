package com.peusoaresf.pokepal.network.dto

import com.peusoaresf.pokepal.database.entity.PokemonEntity
import com.squareup.moshi.Json

data class PokemonDTO(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites?,
    val is_default: Boolean,
    val types: Array<TypeItem>,
    val stats: Array<StatItem>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PokemonDTO

        if (id != other.id) return false
        if (name != other.name) return false
        if (sprites != other.sprites) return false
        if (is_default != other.is_default) return false
        if (!types.contentEquals(other.types)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + sprites.hashCode()
        result = 31 * result + is_default.hashCode()
        result = 31 * result + types.contentHashCode()
        return result
    }
}

fun Array<StatItem>.getStatByName(name: String): StatItem? {
    return find { stat -> stat.stat.name == name }
}

fun List<PokemonDTO>.asDatabaseModel(): List<PokemonEntity> {
    return map { pokemonDTO ->
        val primaryType = pokemonDTO.types.find { it.slot == 1 }?.type?.name ?: "unknown"
        val secondaryType = pokemonDTO.types.find { it.slot == 2 }?.type?.name

        PokemonEntity(
            id = pokemonDTO.id,
            name = pokemonDTO.name,
            height = pokemonDTO.height,
            weight = pokemonDTO.weight,
            image_url = pokemonDTO.sprites?.other?.officialArtwork?.front_default ?: "",
            is_default = pokemonDTO.is_default,
            primary_type = primaryType,
            secondary_type = secondaryType,
            base_hp = pokemonDTO.stats.getStatByName("hp")?.base_stat ?: -1,
            base_hp_effort = pokemonDTO.stats.getStatByName("hp")?.effort ?: -1,
            base_attack = pokemonDTO.stats.getStatByName("attack")?.base_stat ?: -1,
            base_attack_effort = pokemonDTO.stats.getStatByName("attack")?.effort ?: -1,
            base_defense = pokemonDTO.stats.getStatByName("defense")?.base_stat ?: -1,
            base_defense_effort = pokemonDTO.stats.getStatByName("defense")?.effort ?: -1,
            base_sp_atk = pokemonDTO.stats.getStatByName("special-attack")?.base_stat ?: -1,
            base_sp_atk_effort = pokemonDTO.stats.getStatByName("special-attack")?.effort ?: -1,
            base_sp_def = pokemonDTO.stats.getStatByName("special-defense")?.base_stat ?: -1,
            base_sp_def_effort = pokemonDTO.stats.getStatByName("special-defense")?.effort ?: -1,
            base_speed = pokemonDTO.stats.getStatByName("speed")?.base_stat ?: -1,
            base_speed_effort = pokemonDTO.stats.getStatByName("speed")?.effort ?: -1
        )
    }
}

data class Sprites(val other: SpritesOther?)

data class SpritesOther(
    @Json(name = "official-artwork")
    val officialArtwork: OfficialArtwork?)

data class OfficialArtwork(val front_default: String?)

data class TypeItem(
    val slot: Int,
    val type: Type
)

data class Type(val name: String)

data class StatItem(
    val base_stat: Int,
    val effort: Int,
    val stat: Stat
)

data class Stat(val name: String)