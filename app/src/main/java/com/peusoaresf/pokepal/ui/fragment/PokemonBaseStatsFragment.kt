package com.peusoaresf.pokepal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peusoaresf.pokepal.databinding.FragmentPokemonBaseStatsBinding
import com.peusoaresf.pokepal.domain.Pokemon

class PokemonBaseStatsFragment(): Fragment() {
    companion object {
        const val POKEMON = "pokemon"

        fun newInstance(pokemon: Pokemon): PokemonBaseStatsFragment {
            val fragment = PokemonBaseStatsFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(POKEMON, pokemon)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPokemonBaseStatsBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.pokemon = requireArguments().getParcelable(PokemonAboutFragment.POKEMON)

        return binding.root
    }
}