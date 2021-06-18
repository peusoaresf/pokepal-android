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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.peusoaresf.pokepal.R
import com.peusoaresf.pokepal.database.getDatabase
import com.peusoaresf.pokepal.network.Network
import com.peusoaresf.pokepal.repository.PokemonRepository
import com.peusoaresf.pokepal.ui.adapter.PokedexAdapter
import com.peusoaresf.pokepal.ui.viewmodel.PokedexViewModel
import com.peusoaresf.pokepal.ui.viewmodel.PokedexViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PokedexFragment: Fragment() {
    private val viewModel: PokedexViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(this, PokedexViewModelFactory(activity.application))
            .get(PokedexViewModel::class.java)
    }

    private val pokedexAdapter = PokedexAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pokedex, container, false)

        val recyclerViewPokedex = root.findViewById<RecyclerView>(R.id.recycler_view_pokedex)
        recyclerViewPokedex.adapter = pokedexAdapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshProgress.observe(viewLifecycleOwner, Observer { progress ->
            val textProgress = view.findViewById<TextView>(R.id.text_progress)
            textProgress.text = progress
        })

        viewModel.pokemonsLoaded.observe(viewLifecycleOwner, Observer { pokemonsLoaded ->
            val textPokemonCount = view.findViewById<TextView>(R.id.text_pokemon_count)
            textPokemonCount.text = pokemonsLoaded
        })

        viewModel.pokemons.observe(viewLifecycleOwner, Observer { pokemons ->
            pokedexAdapter.submitList(pokemons)
        })

        val buttonRefreshPokemons = view.findViewById<Button>(R.id.button_refresh_pokemons)
        buttonRefreshPokemons.setOnClickListener {
            try {
                viewModel.refreshPokemons()
            } catch (e: Exception) {
                val msg = "Error: ${e.message ?: "UnknownError"}"

                Toast.makeText(this@PokedexFragment.context, msg, Toast.LENGTH_LONG).show()
                Log.i("MainActivity", msg)
            }
        }
    }
}