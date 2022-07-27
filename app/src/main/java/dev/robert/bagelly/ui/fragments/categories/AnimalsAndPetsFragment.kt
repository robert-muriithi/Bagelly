package dev.robert.bagelly.ui.fragments.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentAnimalsAndPetsBinding

@AndroidEntryPoint
class AnimalsAndPetsFragment : Fragment() {
    private lateinit var binding: FragmentAnimalsAndPetsBinding
    private lateinit var  listItem : Array<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAnimalsAndPetsBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.animalAndPetsFragmentToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listItem = resources.getStringArray(R.array.animals)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listItem)
        binding.animalAndPetsFragmentListView.adapter = adapter

        binding.animalAndPetsFragmentListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val value = adapter.getItem(position)
                findNavController().navigate(R.id.action_animalsAndPetsFragment_to_animalsAndPetsItemsFragment)
                Toast.makeText(
                    requireContext(),
                    value,
                    Toast.LENGTH_SHORT
                ).show()
            }



        return view
    }


}