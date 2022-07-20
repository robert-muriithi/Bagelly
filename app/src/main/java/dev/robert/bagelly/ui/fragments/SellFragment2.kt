package dev.robert.bagelly.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentSell2Binding
@AndroidEntryPoint
class SellFragment2 : Fragment() {
    private lateinit var binding : FragmentSell2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSell2Binding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

}