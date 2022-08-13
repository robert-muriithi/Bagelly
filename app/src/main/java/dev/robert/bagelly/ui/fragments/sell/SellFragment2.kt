package dev.robert.bagelly.ui.fragments.sell

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ServerValue
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.databinding.FragmentSell2Binding
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.ui.fragments.sell.viewmodel.SellViewModel
import dev.robert.bagelly.utils.CheckInternet
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

@AndroidEntryPoint
class SellFragment2 : Fragment() {
    private lateinit var binding: FragmentSell2Binding
    private val args: SellFragment2Args by navArgs()
    private val viewModel: SellViewModel by viewModels()
    private val imagesUrls: ArrayList<String> = ArrayList()
    private var imagesList: ArrayList<Uri> = ArrayList()
    private var imageUrl1: String? = null
    private var imageUrl2: String? = null
    private var imageUrl3: String? = null
    private lateinit var id: String


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSell2Binding.inflate(inflater, container, false)
        val view = binding.root
        id = FirebaseAuth.getInstance().currentUser?.uid.toString()

        imagesList = args.sellArgs.images!!

        for (i in 0 until imagesList.size) {
            imagesUrls.add(imagesList[i].toString())
            imageUrl1 = imagesList[0].toString()
            imageUrl2 = imagesList[1].toString()
            imageUrl3 = imagesList[2].toString()
        }

        binding.finishButton.setOnClickListener {
            val category = args.sellArgs.category
            val subCategory = args.sellArgs.subCategory
            val itemName = binding.nameInputLayout.editText?.text.toString()
            val location = binding.locationInputLayout.editText?.text.toString()
            val condition = binding.conditionInputLayout.editText?.text.toString()
            val description = binding.descriptionInputLayout.editText?.text.toString()
            val price = binding.priceInputLayout.editText?.text.toString()
            val uniqueItemId = UUID.randomUUID().toString()
            val datePosted : String = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time).toString()

            val sell = Sell(
               uniqueItemId,
               id,
               itemName,
                location,
                condition,
                description,
                price,
                category.toString(),
                subCategory.toString(),
                datePosted
            )

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
                            viewModel.sell(sell, imagesList)
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
                                        "Item has been uploaded successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //requireActivity().onBackPressed()
                                }
                                is Resource.Error -> {
                                    binding.progressBar.isVisible = false
                                    binding.finishButton.text = "Finish"
                                }
                            }
                        }
                    } else {
                        Snackbar.make(
                            requireView(),
                            "Connect to internet and try again",
                            Snackbar.LENGTH_LONG
                        )
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