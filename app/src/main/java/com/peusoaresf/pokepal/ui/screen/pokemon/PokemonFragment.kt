package com.peusoaresf.pokepal.ui.screen.pokemon

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.peusoaresf.pokepal.R
import com.peusoaresf.pokepal.databinding.FragmentPokemonBinding

class PokemonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPokemonBinding.inflate(inflater)
        val selectedPokemon = PokemonFragmentArgs.fromBundle(requireArguments()).pokemon

        binding.lifecycleOwner = viewLifecycleOwner
        binding.pokemon = selectedPokemon
        binding.viewPagerDetails.adapter = PokemonPagerAdapter(this.requireActivity(), selectedPokemon)

        TabLayoutMediator(binding.tabLayoutDetails, binding.viewPagerDetails) { tab, position ->
            tab.text = when (position) {
                0 -> resources.getString(R.string.about)
                1 -> resources.getString(R.string.base_stats)
                else -> "Unknown"
            }
        }.attach()

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_overflow_pokemon, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_favorites -> Toast.makeText(context, "Add to favorites", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}