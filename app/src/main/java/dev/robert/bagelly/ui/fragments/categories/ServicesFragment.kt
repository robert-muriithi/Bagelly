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
import dev.robert.bagelly.databinding.FragmentServicesBinding

@AndroidEntryPoint
class ServicesFragment : Fragment() {
    private lateinit var binding: FragmentServicesBinding
    private lateinit var listItem: Array<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentServicesBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.servicesFragmentToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listItem = resources.getStringArray(R.array.services)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listItem)
        binding.servicesFragmentListView.adapter = adapter

        binding.servicesFragmentListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val value = adapter.getItem(position)
                findNavController().navigate(R.id.action_servicesFragment_to_servicesItemsFragment)
                Toast.makeText(
                    requireContext(),
                    value,
                    Toast.LENGTH_SHORT
                ).show()


            }

        return view
    }
}