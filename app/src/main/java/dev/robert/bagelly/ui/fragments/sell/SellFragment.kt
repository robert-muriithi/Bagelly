package dev.robert.bagelly.ui.fragments.sell

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentSellBinding
import dev.robert.bagelly.model.Sell

@AndroidEntryPoint
class SellFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentSellBinding
    private var IMAGE_REQUEST_CODE_1 = 1
    private var IMAGE_REQUEST_CODE_2 = 2
    private var IMAGE_REQUEST_CODE_3 = 3

    private val imagesArrayList: ArrayList<String> = ArrayList()
    private var imageURI1: Uri? = null
    private var imageURI2: Uri? = null
    private var imageURI3: Uri? = null

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

        binding.addPhotoOne.setOnClickListener { openFileChooser(IMAGE_REQUEST_CODE_1) }
        binding.addPhotoTwo.setOnClickListener { openFileChooser(IMAGE_REQUEST_CODE_2) }
        binding.addPhotoThree.setOnClickListener { openFileChooser(IMAGE_REQUEST_CODE_3) }
        binding.photoOne.setOnClickListener { openFileChooser(IMAGE_REQUEST_CODE_1) }
        binding.photoTwo.setOnClickListener { openFileChooser(IMAGE_REQUEST_CODE_2) }
        binding.photoThree.setOnClickListener { openFileChooser(IMAGE_REQUEST_CODE_3) }

        binding.nextButton.setOnClickListener {
            val sellCategory = binding.categorySpinner.selectedItem.toString().trim()
            val sellSubCategory = binding.subCategorySpinner.selectedItem.toString().trim()
            when {
                imageURI1 == null -> {
                    Toast.makeText(requireContext(), "Please add all photos", Toast.LENGTH_SHORT).show()
                }
                imageURI2 == null -> {
                    Toast.makeText(requireContext(), "Please add all photos", Toast.LENGTH_SHORT).show()
                }
                imageURI3 == null -> {
                    Toast.makeText(requireContext(), "Please add all photos", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val sell = Sell(sellCategory, sellSubCategory, imagesArrayList)
                    Toast.makeText(requireContext(), "${imagesArrayList.size}", Toast.LENGTH_SHORT).show()
                    val action = SellFragmentDirections.actionSellFragmentToSellFragment2(sell)
                    findNavController().navigate(action)
                }
            }
        }
        return view
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        if (binding.categorySpinner.selectedItem.equals("Agriculture and Food")) {
            val category = resources.getStringArray(R.array.agriculture)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
        }
        if (binding.categorySpinner.selectedItem == "Animals and Pets") {
            val category = resources.getStringArray(R.array.animals)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            // binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Commercial equipment and Tools") {
            val category = resources.getStringArray(R.array.equipment)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            //binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Electronics") {
            val category = resources.getStringArray(R.array.electronics)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            //binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Fashion") {
            val category = resources.getStringArray(R.array.fashion)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            // binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Health and Beauty") {
            val category = resources.getStringArray(R.array.health)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            //binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Home furniture and Appliances") {
            val category = resources.getStringArray(R.array.furniture)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            // binding.subCategorySpinner.onItemSelectedListener = this
        }

        if (binding.categorySpinner.selectedItem == "Mobile phones and tablets") {
            val category = resources.getStringArray(R.array.mobile)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            //binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Property") {
            val category = resources.getStringArray(R.array.property)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            // spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            //binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Babies and kits items") {
            val category = resources.getStringArray(R.array.babies)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            //binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Repair and construction") {
            val category = resources.getStringArray(R.array.repair)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            // binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Services") {
            val category = resources.getStringArray(R.array.services)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            //binding.subCategorySpinner.onItemSelectedListener = this
        }
        if (binding.categorySpinner.selectedItem == "Vehicles") {
            val category = resources.getStringArray(R.array.vehicles)
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
            //spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            binding.subCategorySpinner.adapter = spinnerAdapter
            //binding.subCategorySpinner.onItemSelectedListener = this
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun openFileChooser(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == IMAGE_REQUEST_CODE_1 && resultCode == RESULT_OK && data != null && data.data != null -> {
                imageURI1 = data.data
                imageURI1?.let { imagesArrayList.add(it.toString()) }
                binding.photoOne.setImageURI(imageURI1)
                binding.addPhotoOne.isVisible = false
            }
            requestCode == IMAGE_REQUEST_CODE_2 && resultCode == RESULT_OK && data != null && data.data != null -> {
                imageURI2 = data.data
                imageURI2?.let { imagesArrayList.add(it.toString()) }
                binding.photoTwo.setImageURI(imageURI2)
                binding.addPhotoTwo.isVisible = false
            }
            requestCode == IMAGE_REQUEST_CODE_3 && resultCode == RESULT_OK && data != null && data.data != null -> {
                imageURI3 = data.data
                imageURI3?.let { imagesArrayList.add(it.toString()) }
                binding.photoThree.setImageURI(imageURI3)
                binding.addPhotoThree.isVisible = false
            }
            requestCode == IMAGE_REQUEST_CODE_1 && data == null -> {
                binding.addPhotoOne.visibility = View.VISIBLE
            }
            requestCode == IMAGE_REQUEST_CODE_2 && data == null -> {
                binding.addPhotoTwo.visibility = View.VISIBLE
            }
            requestCode == IMAGE_REQUEST_CODE_3 && data == null -> {
                binding.addPhotoThree.visibility = View.VISIBLE
            }
        }

    }
}