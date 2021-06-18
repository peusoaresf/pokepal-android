package com.peusoaresf.pokepal.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.peusoaresf.pokepal.R
import com.peusoaresf.pokepal.database.getDatabase
import com.peusoaresf.pokepal.databinding.FragmentPokedexBinding
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
        val binding: FragmentPokedexBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_pokedex, container, false)

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_refresh -> Toast.makeText(this.context, "Refresh pokemons", Toast.LENGTH_SHORT).show()
            R.id.menu_item_about -> this.findNavController().navigate(
                PokedexFragmentDirections.actionPokedexFragmentToAboutFragment()
            )
        }
        return super.onOptionsItemSelected(item)
    }
}