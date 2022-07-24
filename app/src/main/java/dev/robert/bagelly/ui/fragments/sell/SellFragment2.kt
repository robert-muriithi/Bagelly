package dev.robert.bagelly.ui.fragments.sell

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.databinding.FragmentSell2Binding
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.model.SellCategory
import dev.robert.bagelly.ui.fragments.sell.viewmodel.SellViewModel
import dev.robert.bagelly.utils.CheckInternet
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SellFragment2 : Fragment() {
    private lateinit var binding: FragmentSell2Binding
    private val args: SellFragment2Args by navArgs()
    private val viewModel: SellViewModel by viewModels()
    private val imagesUrls: ArrayList<String> = ArrayList()
    private var imagesList: ArrayList<Uri> = ArrayList()
    var imageUrl1: String? = null
    var imageUrl2: String? = null
    var imageUrl3: String? = null


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSell2Binding.inflate(inflater, container, false)
        val view = binding.root

        imagesList = args.sellCategory.images!!
        imagesList.forEach {
            imagesUrls.add(it.toString())
        }
        imageUrl1 = imagesUrls[0]
        imageUrl2 = imagesUrls[1]
        imageUrl3 = imagesUrls[2]

        binding.finishButton.setOnClickListener {
            val category = args.sellCategory.category
            val subCategory = args.sellCategory.subCategory
            val itemName = binding.nameInputLayout.editText?.text.toString()
            val location = binding.locationInputLayout.editText?.text.toString()
            val condition = binding.conditionInputLayout.editText?.text.toString()
            val description = binding.descriptionInputLayout.editText?.text.toString()
            val price = binding.priceInputLayout.editText?.text.toString()
            val sellCategory = SellCategory(category, subCategory, imagesList)
            val sell = Sell(itemName, location, condition, description, price, sellCategory)

            when {
                itemName.trim().isEmpty() -> {
                    binding.nameInputLayout.error = "Field can't be empty!"
                    binding.nameInputLayout.isErrorEnabled = true
                }
                location.trim().isEmpty() -> {
                    binding.locationInputLayout.error = "Field can't be empty!"
                    binding.locationInputLayout.isErrorEnabled = true
                }
                condition.trim().isEmpty() -> {
                    binding.conditionInputLayout.error = "Field can't be empty!"
                    binding.conditionInputLayout.isErrorEnabled = true
                }
                description.trim().isEmpty() -> {
                    binding.descriptionInputLayout.error = "Field can't be empty!"
                    binding.descriptionInputLayout.isErrorEnabled = true

                }
                price.trim().isEmpty() -> {
                    binding.priceInputLayout.error = "Field can't be empty!"
                    binding.priceInputLayout.isErrorEnabled = true
                }
                else -> {
                    binding.nameInputLayout.isErrorEnabled = false
                    binding.locationInputLayout.isErrorEnabled = false
                    binding.conditionInputLayout.isErrorEnabled = false
                    binding.descriptionInputLayout.isErrorEnabled = false
                    binding.priceInputLayout.isErrorEnabled = false
                    if (CheckInternet.isConnected(requireContext())) {
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.sell(sell)
                        }
                        viewModel.sell.observe(viewLifecycleOwner) {
                            when (it) {
                                is Resource.Loading -> {
                                    binding.progressBar.isVisible = true
                                    binding.finishButton.text = "Uploading.."
                                }
                                is Resource.Success -> {
                                    binding.progressBar.isVisible = false
                                    binding.finishButton.text = "Finish"
                                    Toast.makeText(
                                        requireContext(),
                                        "${it.data} has been uploaded successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                is Resource.Error -> {
                                    binding.progressBar.isVisible = false
                                    binding.finishButton.text = "Finish"
                                }
                            }
                        }
                    } else {
                        //show snackbar
                        Snackbar.make(requireView(), "Connect to internet and try again", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE") { }
                            .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
                            .show()
                    }

                }
            }

        }

        return view
    }

}