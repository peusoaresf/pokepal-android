package com.peusoaresf.pokepal.network.service

import com.peusoaresf.pokepal.network.dto.PagedResult
import com.peusoaresf.pokepal.network.dto.PokemonDTO
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon")
    fun getPokemons(): Deferred<PagedResult>

    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") name: String): Deferred<PokemonDTO>
}