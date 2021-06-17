package com.peusoaresf.pokepal.network.service

import com.peusoaresf.pokepal.network.dto.PagedResult
import com.peusoaresf.pokepal.network.dto.PokemonDTO
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    fun getPokemons(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0): Deferred<PagedResult>

    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") name: String): Deferred<PokemonDTO>
}