package com.peusoaresf.pokepal.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.peusoaresf.pokepal.R
import com.peusoaresf.pokepal.databinding.FragmentPokemonBinding
import com.peusoaresf.pokepal.ui.adapter.PokemonDetailsPagerAdapter

class PokemonFragment : Fragment() {
    private lateinit var binding: FragmentPokemonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonBinding.inflate(inflater)
        val selectedPokemon = PokemonFragmentArgs.fromBundle(requireArguments()).pokemon

        handleOrientationChange(this.resources.configuration)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.pokemon = selectedPokemon
        binding.viewPagerDetails.adapter = PokemonDetailsPagerAdapter(selectedPokemon, this.requireActivity())

        TabLayoutMediator(binding.tabLayoutDetails, binding.viewPagerDetails) { tab, position ->
            tab.text = when (position) {
                0 -> resources.getString(R.string.about)
                1 -> resources.getString(R.string.base_stats)
                else -> "Unknown"
            }
        }.attach()

        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        handleOrientationChange(newConfig)
    }

    // TODO: find a better way to deal with different orientations.
    // Maybe create different layout files for screens that aren't as responsive by default?
    private fun handleOrientationChange(newConfig: Configuration) {
        val viewPagerLayoutParams = binding.viewPagerDetails.layoutParams as ConstraintLayout.LayoutParams

        when (newConfig.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                viewPagerLayoutParams.bottomToBottom = binding.constraintLayoutPokemon.id
                binding.viewPagerDetails.minimumHeight = 0
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                viewPagerLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.UNSET
                binding.viewPagerDetails.minimumHeight = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 250f, resources.displayMetrics
                ).toInt()
            }
        }

        binding.viewPagerDetails.layoutParams = viewPagerLayoutParams
    }
}