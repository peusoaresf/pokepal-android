package com.peusoaresf.pokepal.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.peusoaresf.pokepal.domain.Pokemon
import com.peusoaresf.pokepal.ui.fragment.PokemonAboutFragment
import com.peusoaresf.pokepal.ui.fragment.PokemonBaseStatsFragment

class PokemonDetailsPagerAdapter(
    private val pokemon: Pokemon,
    fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PokemonAboutFragment.newInstance(pokemon)
            1 -> PokemonBaseStatsFragment.newInstance(pokemon)
            else -> throw IllegalArgumentException("Fragment with position $position not configured")
        }
    }
}