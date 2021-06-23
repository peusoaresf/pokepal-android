package com.peusoaresf.pokepal.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val spriteUrl: String): Parcelable {
    val number = "#$id"
}