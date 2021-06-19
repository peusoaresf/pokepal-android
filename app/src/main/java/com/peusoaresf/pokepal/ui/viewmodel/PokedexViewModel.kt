package com.peusoaresf.pokepal.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.peusoaresf.pokepal.database.dao.PokemonDao
import com.peusoaresf.pokepal.database.getDatabase
import com.peusoaresf.pokepal.domain.Pokemon
import com.peusoaresf.pokepal.network.Network
import com.peusoaresf.pokepal.repository.PokemonRepository
import com.peusoaresf.pokepal.worker.PokedexSyncWorker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokedexViewModel(
    application: Application,
    val lifecycleOwner: LifecycleOwner,
    val pokemonRepository: PokemonRepository
): AndroidViewModel(application) {

    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String>
        get() = _showErrorMessage

    private val _refreshProgress = MutableLiveData<Int>()
    val refreshProgressText = Transformations.map(_refreshProgress) {
        progress -> "$progress%"
    }

    val pokemons = pokemonRepository.pokemons

    val pokemonsLoadedText = Transformations.map(pokemons) {
        pokemons -> "${pokemons.size.toString()} pokemons loaded"
    }

    fun refreshPokedex() {
        val workerRequest = OneTimeWorkRequest.Builder(PokedexSyncWorker::class.java).build()

        val workManager = WorkManager.getInstance()
        workManager
            .beginWith(workerRequest)
            .enqueue()

        workManager.getWorkInfoByIdLiveData(workerRequest.id).observe(lifecycleOwner, Observer { workInfo ->
            workInfo?.let {
                val progress = workInfo.progress.getInt("progress", 0)

                _refreshProgress.postValue(progress)
            }
        })
    }

    // TODO: How to get error from worker?
    fun showErrorMessageDone() {
        _showErrorMessage.value = null
    }
}

class PokedexViewModelFactory(
    private val application: Application,
    private val lifecycleOwner: LifecycleOwner
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokedexViewModel::class.java)) {
            val pokemonRepository = PokemonRepository(
                Dispatchers.IO,
                getDatabase(application.applicationContext).pokemonDao,
                Network.pokemonService,
            )

            @Suppress("UNCHECKED_CAST")
            return PokedexViewModel(application, lifecycleOwner, pokemonRepository) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}