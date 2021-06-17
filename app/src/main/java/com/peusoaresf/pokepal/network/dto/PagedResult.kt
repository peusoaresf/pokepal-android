package com.peusoaresf.pokepal.network.dto

data class NamedResource(
    val name: String,
    val url: String
)

data class PagedResult(
    val count: Int,
    val next: String?,
    val results: List<NamedResource>
)