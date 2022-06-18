package com.peusoaresf.pokepal.ui.screen.pokedex

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.peusoaresf.pokepal.database.getDatabase
import com.peusoaresf.pokepal.domain.Pokemon
import com.peusoaresf.pokepal.network.Network
import com.peusoaresf.pokepal.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PokedexViewModel(
    application: Application,
    val pokemonRepository: PokemonRepository,
    searchQueryDataSource: SearchQueryDataSource): AndroidViewModel(application) {

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

    val filter = pokemonRepository.filter

    init {
        _isRefreshing.value = false

        viewModelScope.launch {
            searchQueryDataSource.getSearchQuery()
                .debounce(500)
                .collect { query ->
                    pokemonRepository.setPokemonFilter(query)
                }
        }
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
    private val application: Application,
    private val searchQueryDataSource: SearchQueryDataSource): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokedexViewModel::class.java)) {
            val pokemonRepository = PokemonRepository(
                Dispatchers.IO,
                getDatabase(application.applicationContext).pokemonDao,
                Network.pokemonService,
            )

            @Suppress("UNCHECKED_CAST")
            return PokedexViewModel(application, pokemonRepository, searchQueryDataSource) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}