package com.peusoaresf.pokepal.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.peusoaresf.pokepal.database.dao.PokemonDao
import com.peusoaresf.pokepal.database.getDatabase
import com.peusoaresf.pokepal.domain.Pokemon
import com.peusoaresf.pokepal.network.Network
import com.peusoaresf.pokepal.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokedexViewModel(
    application: Application,
    val pokemonRepository: PokemonRepository): AndroidViewModel(application) {

    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String>
        get() = _showErrorMessage

    private val _navigateToSelectedPokemon = MutableLiveData<Pokemon>()
    val navigateToSelectedPokemon: LiveData<Pokemon>
        get() = _navigateToSelectedPokemon

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    val refreshProgress = pokemonRepository.refreshProgress

    val pokemons = pokemonRepository.pokemons

    init {
        _isRefreshing.value = false
    }

    fun refreshPokemons() = viewModelScope.launch {
        _isRefreshing.value = true

        try {
            pokemonRepository.refreshPokemons()
        } catch (e: Exception) {
            val msg = "Error: ${e.message ?: "UnknownError"}"

            Log.i("PokedexViewModel", msg)
            _showErrorMessage.value = msg
        } finally {
            _isRefreshing.value = false
        }
    }

    fun showErrorMessageDone() {
        _showErrorMessage.value = null
    }

    fun displayPokemonDetails(pokemon: Pokemon) {
        _navigateToSelectedPokemon.value = pokemon
    }

    fun displayPokemonDetailsDone() {
        _navigateToSelectedPokemon.value = null
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