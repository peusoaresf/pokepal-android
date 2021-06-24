package com.peusoaresf.pokepal.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val primaryType: PokemonType,
    val secondaryType: PokemonType?,
    val height: Int,
    val weight: Int,
    val baseStats: PokemonStats): Parcelable {

    val number = "#$id"
    val heightInCm = "${String.format("%.2f", height / 10.0)} cm"
    val weightInKg = "${String.format("%.1f", weight / 10.0)} kg"
}