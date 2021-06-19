package com.peusoaresf.pokepal.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.peusoaresf.pokepal.database.getDatabase
import com.peusoaresf.pokepal.network.Network
import com.peusoaresf.pokepal.network.dto.asDatabaseModel
import com.peusoaresf.pokepal.notification.PokedexSyncNotification
import com.peusoaresf.pokepal.repository.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class PokedexSyncWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORKER_NAME = "PokedexSyncWorker"
    }

    private val pokemonService = Network.pokemonService
    private val pokemonDao = getDatabase(applicationContext).pokemonDao
    private val pokedexSyncNotification = PokedexSyncNotification(applicationContext)

    override suspend fun doWork(): Result {
        Log.i("PokedexSyncWorker", "Starting pokedex sync")

        return try {
            pokemonDao.deleteAll()

            notifyProgress(0)

            var offset = 0
            var hasNextPage: Boolean
            var persistedPokemons = 0

            do {
                val pokemonResources = pokemonService.getPokemons(offset = offset).await()

                val pokemonDtos = pokemonResources.results.map { resource ->
                    Network.pokemonService.getPokemon(resource.name)
                }.awaitAll()

                pokemonDao.insertAll(*pokemonDtos.asDatabaseModel().toTypedArray())

                offset += 20
                hasNextPage = pokemonResources.next != null
                persistedPokemons += pokemonDtos.size

                val progress = (persistedPokemons.toDouble() / pokemonResources.count) * 100

                notifyProgress(progress.toInt())
            } while (hasNextPage)

            notifyProgress(100)

            Result.success()
        } catch (e: Exception) {
            Log.i("Worker", e.message.toString())
            Result.failure()
        }
    }

    private fun notifyProgress(progress: Int) {
        setProgressAsync(Data.Builder().putInt("progress", progress).build())
        pokedexSyncNotification.notifyProgress(progress)
    }
}