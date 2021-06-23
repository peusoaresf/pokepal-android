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

    private val _refreshProgress = MutableLiveData<Int>()
    val refreshProgress: LiveData<Int>
        get() = _refreshProgress

    val pokemons = Transformations.map(pokemonDao.getPokemons()) {
        it.asDomainModel()
    }

    suspend fun refreshPokemons() = withContext(externalContext) {
        pokemonDao.deleteAll()
        _refreshProgress.postValue(0)

        var offset = 0
        var loadedCount = 0
        var hasNextPage: Boolean

        do {
            val pokemonResources = pokemonService.getPokemons(offset = offset).await()

            val pokemonDtos = pokemonResources.results.map { resource ->
                Network.pokemonService.getPokemon(resource.name)
            }.awaitAll()

            pokemonDao.insertAll(*pokemonDtos.asDatabaseModel().toTypedArray())

            offset += 20
            loadedCount += pokemonDtos.size
            hasNextPage = pokemonResources.next != null
            val progress = (loadedCount.toDouble() / pokemonResources.count) * 100

            _refreshProgress.postValue(progress.toInt())
        } while (hasNextPage)

        _refreshProgress.postValue(100)
    }
}