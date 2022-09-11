package dev.robert.bagelly.ui.fragments.sell

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentSellsBinding
import dev.robert.bagelly.ui.fragments.sell.viewmodel.SellViewModel

@AndroidEntryPoint
class SellsFragment : Fragment() {
    private lateinit var binding: FragmentSellsBinding
    private val viewModel by viewModels<SellViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSellsBinding.inflate(inflater, container, false)
        val view = binding.root



        return view
    }


}