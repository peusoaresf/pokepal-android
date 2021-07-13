package com.peusoaresf.pokepal.ui.screen.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peusoaresf.pokepal.databinding.FragmentPokemonAboutBinding
import com.peusoaresf.pokepal.domain.Pokemon

class PokemonAboutFragment(): Fragment() {
    companion object {
        const val POKEMON = "pokemon"

        fun newInstance(pokemon: Pokemon): PokemonAboutFragment {
            val fragment = PokemonAboutFragment()
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
        val binding = FragmentPokemonAboutBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.pokemon = requireArguments().getParcelable(POKEMON)

        return binding.root
    }
}