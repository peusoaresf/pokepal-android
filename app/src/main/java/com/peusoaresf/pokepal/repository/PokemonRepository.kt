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

    private val _refreshProgress = MutableLiveData<Double>()
    val refreshProgress: LiveData<Double>
        get() = _refreshProgress

    val pokemons = Transformations.map(pokemonDao.getPokemons()) {
        it.asDomainModel()
    }

    suspend fun refreshPokemons() = withContext(externalContext) {
        pokemonDao.deleteAll()
        _refreshProgress.postValue(0.0)

        var offset = 0
        var hasNextPage: Boolean

        do {
            val pokemonResources = pokemonService.getPokemons(offset = offset).await()

            val pokemonDtos = pokemonResources.results.map { resource ->
                Network.pokemonService.getPokemon(resource.name)
            }.awaitAll()

            pokemonDao.insertAll(*pokemonDtos.asDatabaseModel().toTypedArray())

            offset += 20
            hasNextPage = pokemonResources.next != null

            _refreshProgress.postValue( ((pokemons.value?.size?.toDouble() ?: 0.0) / pokemonResources.count) * 100)
        } while (hasNextPage)

        _refreshProgress.postValue(100.00)
    }
}