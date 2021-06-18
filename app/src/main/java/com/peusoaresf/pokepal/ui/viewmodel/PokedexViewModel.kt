package com.peusoaresf.pokepal.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.peusoaresf.pokepal.database.dao.PokemonDao
import com.peusoaresf.pokepal.database.getDatabase
import com.peusoaresf.pokepal.network.Network
import com.peusoaresf.pokepal.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokedexViewModel(
    application: Application,
    val pokemonRepository: PokemonRepository): AndroidViewModel(application) {

    val refreshProgress = Transformations.map(pokemonRepository.refreshProgress) {
        progress -> "${"%.2f".format(progress)}%"
    }

    val pokemonsLoaded = Transformations.map(pokemonRepository.pokemons) {
        pokemons -> "${pokemons.size.toString()} pokemons loaded"
    }

    val pokemons = pokemonRepository.pokemons

    fun refreshPokemons() = viewModelScope.launch {
        pokemonRepository.refreshPokemons()
    }
}

class PokedexViewModelFactory(
    private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokedexViewModel::class.java)) {
            val pokemonRepository = PokemonRepository(
                Dispatchers.IO,
                getDatabase(application.applicationContext).pokemonDao,
                Network.pokemonService,
            )

            @Suppress("UNCHECKED_CAST")
            return PokedexViewModel(application, pokemonRepository) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}