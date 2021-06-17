package com.peusoaresf.pokepal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.peusoaresf.pokepal.R
import com.peusoaresf.pokepal.network.Network
import com.peusoaresf.pokepal.network.dto.PokemonDTO
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    // Reminder: inject scopes
    private val uiScope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fillText()
    }

    private fun fillText() = uiScope.launch {
        Log.i("MainActivity", "fillText")
        try {
            // Map DTO to Domain object
            val pokemons = getPokemons()

            val imageView = findViewById<ImageView>(R.id.image_view)
            // Create binding adapter
            Glide.with(imageView.context)
                .load(pokemons[6].sprites.front_default)
                .into(imageView)

        } catch (e: Exception) {
            Log.i("MainActivity", e.message ?: "UnknownError")
        }
    }

    // Reminder: inject dispatchers
    private suspend fun getPokemons() = withContext(Dispatchers.IO) {
        val resources = Network.pokemonService.getPokemons().await()

        val pokemons = resources.results.map { resource ->
            Network.pokemonService.getPokemon(resource.name)
        }.awaitAll()

        return@withContext pokemons
    }
}