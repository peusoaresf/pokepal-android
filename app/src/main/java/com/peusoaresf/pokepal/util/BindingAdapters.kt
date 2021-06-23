package com.peusoaresf.pokepal.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavOptionsDsl
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.peusoaresf.pokepal.R

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