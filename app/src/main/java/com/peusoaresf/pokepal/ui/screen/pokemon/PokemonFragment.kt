package com.peusoaresf.pokepal.ui.screen.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return binding.root
    }
}