package com.peusoaresf.pokepal.network.dto

import com.peusoaresf.pokepal.database.entity.PokemonEntity
import com.squareup.moshi.Json

data class PokemonDTO(
    val id: Int,
    val name: String,
    val sprites: Sprites?,
    val is_default: Boolean,
    val types: Array<TypeItem>
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

fun List<PokemonDTO>.asDatabaseModel(): List<PokemonEntity> {
    return map { pokemonDTO ->
        val primaryType = pokemonDTO.types.find { it.slot == 1 }?.type?.name ?: "unknown"
        val secondaryType = pokemonDTO.types.find { it.slot == 2 }?.type?.name

        PokemonEntity(
            id = pokemonDTO.id,
            name = pokemonDTO.name,
            image_url = pokemonDTO.sprites?.other?.officialArtwork?.front_default ?: "",
            is_default = pokemonDTO.is_default,
            primary_type = primaryType,
            secondary_type = secondaryType
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