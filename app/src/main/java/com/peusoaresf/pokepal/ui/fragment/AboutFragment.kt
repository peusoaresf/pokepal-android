package com.peusoaresf.pokepal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.peusoaresf.pokepal.BuildConfig
import com.peusoaresf.pokepal.R
import com.peusoaresf.pokepal.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAboutBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_about, container, false)

        binding.appVersion = BuildConfig.VERSION_NAME

        return binding.root
    }
}