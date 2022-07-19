package dev.robert.bagelly.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentSellBinding


class SellFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentSellBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSellBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.sellFragmentToolbar)


        val category = resources.getStringArray(R.array.category)
        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
        binding.categorySpinner.adapter = spinnerAdapter
        //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
        binding.categorySpinner.onItemSelectedListener = this

        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_sellFragment_to_sellFragment2)
        }

        return view
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        if (binding.categorySpinner.selectedItem.equals("Agriculture and Food")){
            val category = resources.getStringArray(R.array.agriculture)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
        }
         if (binding.categorySpinner.selectedItem == "Animals and Pets"){
             val category = resources.getStringArray(R.array.animals)
             val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
             //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
             binding.subCategorySpinner.adapter = spinnerAdapter
            // binding.subCategorySpinner.onItemSelectedListener = this
        }
         if (binding.categorySpinner.selectedItem == "Commercial equipment and Tools"){
             val category = resources.getStringArray(R.array.equipment)
             val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
             //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
             binding.subCategorySpinner.adapter = spinnerAdapter
             //binding.subCategorySpinner.onItemSelectedListener = this
        }
         if (binding.categorySpinner.selectedItem == "Electronics"){
             val category = resources.getStringArray(R.array.electronics)
             val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
                 //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
             binding.subCategorySpinner.adapter = spinnerAdapter
             //binding.subCategorySpinner.onItemSelectedListener = this
        }
         if (binding.categorySpinner.selectedItem == "Fashion"){
             val category = resources.getStringArray(R.array.fashion)
             val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
             //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
             binding.subCategorySpinner.adapter = spinnerAdapter
            // binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Health and Beauty"){
            val category = resources.getStringArray(R.array.health)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
                //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            //binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Home furniture and Appliances"){
            val category = resources.getStringArray(R.array.furniture)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
           // binding.subCategorySpinner.onItemSelectedListener = this
        }

        if (binding.categorySpinner.selectedItem == "Mobile phones and tablets"){
            val category = resources.getStringArray(R.array.mobile)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            //binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Property"){
            val category = resources.getStringArray(R.array.property)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
           // spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
                //binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Babies and kits items"){
            val category = resources.getStringArray(R.array.babies)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            //binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Repair and construction"){
            val category = resources.getStringArray(R.array.repair)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
           // binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Services"){
            val category = resources.getStringArray(R.array.services)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            //binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Vehicles"){
            val category = resources.getStringArray(R.array.vehicles)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            //binding.subCategorySpinner.onItemSelectedListener = this
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}