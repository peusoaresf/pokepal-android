package com.peusoaresf.pokepal.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavOptionsDsl
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.peusoaresf.pokepal.R
import com.peusoaresf.pokepal.domain.PokemonType

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .apply(
            RequestOptions()
            .placeholder(R.drawable.animation_loading)
            .error(R.drawable.ic_broken_image))
        .into(imageView)
}

@BindingAdapter("isVisible")
fun setIsVisible(view: View, isVisible: Boolean) {
    view.visibility = when (isVisible) {
        true -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("pokemonType")
fun setPokemonTypeResource(imageView: ImageView, type: PokemonType?) {
    if (type == null) {
        imageView.visibility = View.GONE
        return
    }

    return when (type) {
        PokemonType.BUG -> imageView.setImageResource(R.drawable.type_bug)
        PokemonType.DARK -> imageView.setImageResource(R.drawable.type_dark)
        PokemonType.DRAGON -> imageView.setImageResource(R.drawable.type_dragon)
        PokemonType.ELECTRIC -> imageView.setImageResource(R.drawable.type_electric)
        PokemonType.FAIRY -> imageView.setImageResource(R.drawable.type_fairy)
        PokemonType.FIGHTING -> imageView.setImageResource(R.drawable.type_fighting)
        PokemonType.FIRE -> imageView.setImageResource(R.drawable.type_fire)
        PokemonType.FLYING -> imageView.setImageResource(R.drawable.type_flying)
        PokemonType.GHOST -> imageView.setImageResource(R.drawable.type_ghost)
        PokemonType.GRASS -> imageView.setImageResource(R.drawable.type_grass)
        PokemonType.GROUND -> imageView.setImageResource(R.drawable.type_ground)
        PokemonType.ICE -> imageView.setImageResource(R.drawable.type_ice)
        PokemonType.NORMAL -> imageView.setImageResource(R.drawable.type_normal)
        PokemonType.POISON -> imageView.setImageResource(R.drawable.type_poison)
        PokemonType.PSYCHIC -> imageView.setImageResource(R.drawable.type_psychic)
        PokemonType.ROCK -> imageView.setImageResource(R.drawable.type_rock)
        PokemonType.WATER -> imageView.setImageResource(R.drawable.type_water)
        else -> imageView.setImageResource(R.drawable.ic_broken_image)
    }
}
