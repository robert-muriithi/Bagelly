package dev.robert.bagelly.ui.fragmets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentSellBinding

class SellFragment : Fragment() {
    private lateinit var binding: FragmentSellBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSellBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

}