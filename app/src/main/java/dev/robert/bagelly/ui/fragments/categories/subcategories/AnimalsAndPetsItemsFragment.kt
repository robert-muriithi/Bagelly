package dev.robert.bagelly.ui.fragments.categories.subcategories

import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.databinding.FragmentAnimalsAndPetsItemsBinding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

@AndroidEntryPoint
class AnimalsAndPetsItemsFragment : Fragment() {
    private lateinit var binding: FragmentAnimalsAndPetsItemsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAnimalsAndPetsItemsBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.animalAndPetsItemsFragmentToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)



        return view
    }

}