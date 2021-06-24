package com.peusoaresf.pokepal.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stat(val value: Int, val effort: Int): Parcelable

@Parcelize
data class PokemonStats(
    val hp: Stat,
    val attack: Stat,
    val defense: Stat,
    val specialAttack: Stat,
    val specialDefense: Stat,
    val speed: Stat
): Parcelable