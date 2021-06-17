package com.peusoaresf.pokepal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peusoaresf.pokepal.R
import com.peusoaresf.pokepal.adapter.PokedexAdapter
import com.peusoaresf.pokepal.database.dao.PokemonDao
import com.peusoaresf.pokepal.database.getDatabase
import com.peusoaresf.pokepal.network.Network
import com.peusoaresf.pokepal.network.dto.PokemonDTO
import com.peusoaresf.pokepal.repository.PokemonRepository
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val pokedexAdapter = PokedexAdapter()
    private val uiScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var pokemonRepository: PokemonRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerViewPokedex = findViewById<RecyclerView>(R.id.recycler_view_pokedex)
        recyclerViewPokedex.adapter = pokedexAdapter

        pokemonRepository = PokemonRepository(
            Dispatchers.IO,
            getDatabase(applicationContext).pokemonDao,
            Network.pokemonService,
        )

        pokemonRepository.refreshProgress.observe(this, Observer { progress ->
            val textProgress = findViewById<TextView>(R.id.text_progress)
            textProgress.text = "${"%.2f".format(progress)}%"
        })

        pokemonRepository.pokemons.observe(this, Observer { pokemons ->
            val textPokemonCount = findViewById<TextView>(R.id.text_pokemon_count)
            textPokemonCount.text = "${pokemons.size.toString()} pokemons loaded"

            pokedexAdapter.submitList(pokemons)
        })

        val buttonRefreshPokemons = findViewById<Button>(R.id.button_refresh_pokemons)
        buttonRefreshPokemons.setOnClickListener {
            refreshPokemons()
        }
    }

    private fun refreshPokemons() = uiScope.launch {
        try {
            pokemonRepository.refreshPokemons()
        } catch (e: Exception) {
            val msg = "Error: ${e.message ?: "UnknownError"}"

            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_LONG).show()
            Log.i("MainActivity", msg)
        }
    }
}