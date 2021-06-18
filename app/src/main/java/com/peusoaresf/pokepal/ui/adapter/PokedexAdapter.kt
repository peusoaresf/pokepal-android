package com.peusoaresf.pokepal.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peusoaresf.pokepal.R
import com.peusoaresf.pokepal.domain.Pokemon

class PokemonViewHolder
    private constructor(root: View): RecyclerView.ViewHolder(root) {
    private val textPokemonId = root.findViewById<TextView>(R.id.text_pokemon_id)
    private val imagePokemonSprite = root.findViewById<ImageView>(R.id.image_pokemon_sprite)

    fun bind(pokemon: Pokemon) {
        textPokemonId.text = pokemon.id.toString()
        Glide.with(imagePokemonSprite.context)
            .load(pokemon.spriteUrl)
            .into(imagePokemonSprite)
    }

    companion object {
        fun from(parent: ViewGroup): PokemonViewHolder {
            val root = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pokemon, parent, false)
            return PokemonViewHolder(root)
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

class PokedexAdapter: ListAdapter<Pokemon, PokemonViewHolder>(PokemonDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}