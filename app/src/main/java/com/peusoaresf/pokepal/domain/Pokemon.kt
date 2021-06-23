package com.peusoaresf.pokepal.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val spriteUrl: String,
    val primaryType: PokemonType,
    val secondaryType: PokemonType?): Parcelable {
    val number = "#$id"
}