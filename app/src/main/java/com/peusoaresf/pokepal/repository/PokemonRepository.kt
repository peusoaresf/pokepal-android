package com.peusoaresf.pokepal.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.peusoaresf.pokepal.database.dao.PokemonDao
import com.peusoaresf.pokepal.database.entity.asDomainModel
import com.peusoaresf.pokepal.network.Network
import com.peusoaresf.pokepal.network.dto.asDatabaseModel
import com.peusoaresf.pokepal.network.service.PokemonService
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PokemonRepository(
    private val externalContext: CoroutineContext,
    private val pokemonDao: PokemonDao,
    private val pokemonService: PokemonService) {

    val pokemons = Transformations.map(pokemonDao.getPokemons()) {
        it.asDomainModel()
    }
}