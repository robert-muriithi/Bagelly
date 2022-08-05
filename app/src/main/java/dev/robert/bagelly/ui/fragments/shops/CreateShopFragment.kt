package dev.robert.bagelly.ui.fragments.shops

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentCreateShopBinding
import dev.robert.bagelly.model.Shop
import dev.robert.bagelly.ui.fragments.shops.viewmodel.ShopsViewModel
import dev.robert.bagelly.utils.CheckInternet
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateShopFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentCreateShopBinding
    private lateinit var listItem: Array<String>
    private var imageUrl = ""
    private var imageUri: Uri? = null
    private val IMAGE_PICK_CODE = 5
    private val viewModel: ShopsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateShopBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()
        (activity as AppCompatActivity).setSupportActionBar(binding.createShopToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listItem = resources.getStringArray(R.array.stores)

        binding.apply {
            shopCategorySpinner.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listItem
            )
            shopCategorySpinner.onItemSelectedListener = this@CreateShopFragment

            btnCreateShop.setOnClickListener {
                val ownerId = FirebaseAuth.getInstance().currentUser?.uid
                val shopCategory = binding.shopCategorySpinner.selectedItem.toString()
                val shopName = binding.shopNam.editText?.text.toString()
                val shopDesc = binding.shopDesc.editText?.text.toString()
                val shopWebsite = binding.shopWebsite.editText?.text.toString()
                val shopPhone = binding.shopPhone.editText?.text.toString()
                val shopLocation = binding.shopLocation.editText?.text.toString()
                val imageUrl: String? = null

                val shop = Shop(
                    ownerId,
                    shopCategory,
                    shopName,
                    shopDesc,
                    shopWebsite,
                    shopPhone,
                    shopLocation,
                    imageUrl
                )

                when {
                    shopName.isEmpty() -> {
                        binding.shopNam.error = "Shop name is required"
                        binding.shopNam.requestFocus()
                    }
                    shopDesc.isEmpty() -> {
                        binding.shopDesc.error = "Shop description is required"
                        binding.shopDesc.requestFocus()
                    }
                    shopLocation.isEmpty() -> {
                        binding.shopLocation.error = "Shop location is required"
                        binding.shopLocation.requestFocus()
                    }
                    imageUri == null ->{
                        Toast.makeText(requireContext(), "Please create a business logo", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        if (CheckInternet.isConnected(requireContext())) {
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.createShop(shop)
                            }
                            viewModel.shops.observe(viewLifecycleOwner) {
                                when (it) {
                                    is Resource.Error -> {
                                        binding.createShopProgress.isVisible = false
                                        Toast.makeText(requireContext(), it.string, Toast.LENGTH_LONG).show()
                                    }
                                    is Resource.Success -> {
                                        binding.createShopProgress.isVisible = false
                                        Toast.makeText(
                                            requireContext(),
                                            "Shop created successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        activity?.onBackPressed()
                                    }
                                    is Resource.Loading -> {
                                        binding.createShopProgress.isVisible = true
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "No internet connection",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

            }

            addLogo.setOnClickListener {
                openFileChooser(IMAGE_PICK_CODE)
            }
        }


        return view
    }

    private fun openFileChooser(requestCode: Int) {
        ImagePicker.with(requireActivity())
            .crop(0.5f, 0.5f)                    //Crop image(Optional),Crop ratio : 1:1(Optional)
            .compress(1024)                 //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )                               //Final image resolution will be less than 1080 x 1080(Optional)
            .start(requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK -> {
                imageUri = data?.data
                binding.addLogo.setImageURI(imageUri)
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p0?.getItemAtPosition(p2).toString()) {
            "Accommodation and travel services" -> {
                binding.shopCategorySpinner.setSelection(0)
            }
            "Automotive services" -> {
                binding.shopCategorySpinner.setSelection(1)
            }
            "Beauty and personal care" -> {
                binding.shopCategorySpinner.setSelection(2)
            }
            "Books, music, and video" -> {
                binding.shopCategorySpinner.setSelection(3)
            }
            "Clothing and accessories" -> {
                binding.shopCategorySpinner.setSelection(4)
            }
            "Electronics and computer" -> {
                binding.shopCategorySpinner.setSelection(5)
            }
            "Food and drink" -> {
                binding.shopCategorySpinner.setSelection(6)
            }
            "Farm inputs Stores" -> {
                binding.shopCategorySpinner.setSelection(7)
            }
            "Fashion and accessories store" -> {
                binding.shopCategorySpinner.setSelection(8)
            }
            "Mobile phone and tablets Store" -> {
                binding.shopCategorySpinner.setSelection(9)
            }
            "Office supplies and stationery stores" -> {
                binding.shopCategorySpinner.setSelection(10)
            }
            "Vehicle dealers" -> {
                binding.shopCategorySpinner.setSelection(11)
            }
            "Other stores" -> {
                binding.shopCategorySpinner.setSelection(12)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}


}