package com.peusoaresf.pokepal.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peusoaresf.pokepal.R
import com.peusoaresf.pokepal.databinding.ListItemPokemonBinding
import com.peusoaresf.pokepal.domain.Pokemon

class PokemonViewHolder
    private constructor(
        private val binding: ListItemPokemonBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: Pokemon, callback: PokemonClick) {
        binding.pokemon = pokemon
        binding.pokemonCallback = callback
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): PokemonViewHolder {
            val binding = ListItemPokemonBinding.inflate(LayoutInflater.from(parent.context))
            return PokemonViewHolder(binding)
        }
    }
}

class PokemonDiffCallback: DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}

class PokemonClick(val lambda: (Pokemon) -> Unit) {
    fun onClick(pokemon: Pokemon) = lambda(pokemon)
}

class PokedexAdapter(
    val callback: PokemonClick
): ListAdapter<Pokemon, PokemonViewHolder>(PokemonDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position), callback)
    }
}