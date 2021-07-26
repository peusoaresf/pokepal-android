package com.peusoaresf.pokepal.ui.screen.pokedex

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.peusoaresf.pokepal.R
import com.peusoaresf.pokepal.databinding.FragmentPokedexBinding

class PokedexFragment: Fragment() {
    private val viewModel: PokedexViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(this, PokedexViewModelFactory(activity.application))
            .get(PokedexViewModel::class.java)
    }

    private val pokedexAdapter by lazy {
        PokedexAdapter(PokemonClick { pokemon ->
            viewModel.displayPokemonDetails(pokemon)
        })
    }

    private lateinit var binding: FragmentPokedexBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokedex, container, false)

        // Allow databinding to observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerViewPokedex.adapter = pokedexAdapter

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pokemons.observe(viewLifecycleOwner, Observer {
            pokemons -> pokedexAdapter.submitList(pokemons)
        })

        viewModel.navigateToSelectedPokemon.observe(viewLifecycleOwner, Observer { pokemon ->
            pokemon?.let {
                this.findNavController().navigate(
                    PokedexFragmentDirections.actionPokedexFragmentToPokemonFragment(pokemon)
                )
                viewModel.displayPokemonDetailsDone()
            }
        })

        viewModel.showErrorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this.context, errorMessage, Toast.LENGTH_LONG).show()
                viewModel.showErrorMessageDone()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_overflow, menu)

        val searchView = (menu.findItem(R.id.menu_item_search).actionView as SearchView)
        searchView.queryHint = getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })

        val searchBar = searchView.findViewById<LinearLayout>(R.id.search_bar)
        searchBar?.layoutTransition = LayoutTransition()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // TODO: first refresh when database is empty and only allow subsequent refreshes once a week.
            // Show toast "Up to date" when the user is not able to refresh
            R.id.menu_item_refresh -> viewModel.refreshPokemons()
            R.id.menu_item_about -> this.findNavController().navigate(
                PokedexFragmentDirections.actionPokedexFragmentToAboutFragment()
            )
        }
        return super.onOptionsItemSelected(item)
    }
}