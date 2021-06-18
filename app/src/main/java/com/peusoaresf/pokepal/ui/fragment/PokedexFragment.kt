package com.peusoaresf.pokepal.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.peusoaresf.pokepal.R
import com.peusoaresf.pokepal.database.getDatabase
import com.peusoaresf.pokepal.network.Network
import com.peusoaresf.pokepal.repository.PokemonRepository
import com.peusoaresf.pokepal.ui.adapter.PokedexAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PokedexFragment: Fragment() {
    private val pokedexAdapter = PokedexAdapter()
    private val uiScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var pokemonRepository: PokemonRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pokedex, container, false)

        val recyclerViewPokedex = root.findViewById<RecyclerView>(R.id.recycler_view_pokedex)
        recyclerViewPokedex.adapter = pokedexAdapter

        pokemonRepository = PokemonRepository(
            Dispatchers.IO,
            getDatabase(root.context).pokemonDao,
            Network.pokemonService,
        )

        pokemonRepository.refreshProgress.observe(this, Observer { progress ->
            val textProgress = root.findViewById<TextView>(R.id.text_progress)
            textProgress.text = "${"%.2f".format(progress)}%"
        })

        pokemonRepository.pokemons.observe(this, Observer { pokemons ->
            val textPokemonCount = root.findViewById<TextView>(R.id.text_pokemon_count)
            textPokemonCount.text = "${pokemons.size.toString()} pokemons loaded"

            pokedexAdapter.submitList(pokemons)
        })

        val buttonRefreshPokemons = root.findViewById<Button>(R.id.button_refresh_pokemons)
        buttonRefreshPokemons.setOnClickListener {
            refreshPokemons()
        }

        return root
    }

    private fun refreshPokemons() = uiScope.launch {
        try {
            pokemonRepository.refreshPokemons()
        } catch (e: Exception) {
            val msg = "Error: ${e.message ?: "UnknownError"}"

            Toast.makeText(this@PokedexFragment.context, msg, Toast.LENGTH_LONG).show()
            Log.i("MainActivity", msg)
        }
    }
}