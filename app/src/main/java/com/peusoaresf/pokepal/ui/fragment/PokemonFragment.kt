package com.peusoaresf.pokepal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.peusoaresf.pokepal.R
import com.peusoaresf.pokepal.databinding.FragmentPokemonBinding

class PokemonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentPokemonBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.pokemon = PokemonFragmentArgs.fromBundle(requireArguments()).pokemon

        return binding.root
    }
}