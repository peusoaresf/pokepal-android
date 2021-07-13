package com.peusoaresf.pokepal.ui.screen.pokemon

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.peusoaresf.pokepal.domain.Pokemon

class PokemonPagerAdapter(
    fa: FragmentActivity,
    private val pokemon: Pokemon): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PokemonAboutFragment.newInstance(pokemon)
            1 -> PokemonBaseStatsFragment.newInstance(pokemon)
            else -> throw IllegalArgumentException("Fragment with position $position not configured")
        }
    }
}