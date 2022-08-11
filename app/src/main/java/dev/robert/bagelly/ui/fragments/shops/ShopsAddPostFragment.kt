package dev.robert.bagelly.ui.fragments.shops

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentShopsAddPostBinding
import dev.robert.bagelly.model.Post
import dev.robert.bagelly.ui.fragments.shops.viewmodel.ShopsViewModel
import dev.robert.bagelly.utils.CheckInternet
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class ShopsAddPostFragment : Fragment() {
    private lateinit var binding: FragmentShopsAddPostBinding
    private val viewModel by viewModels<ShopsViewModel>()
    private val IMAGE_PICK_REQUEST = 0
    private var imageUri: Uri? = null
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShopsAddPostBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.addPostToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.apply {
            addLogo.setOnClickListener {
                openGallery(IMAGE_PICK_REQUEST)
            }
            btnPostAd.setOnClickListener {
                val name = binding.shopNam.editText?.text.toString()
                val type = binding.shopDesc.editText?.text.toString()
                val condition = binding.shopWebsite.editText?.text.toString()
                val desc = binding.shopPhone.editText?.text.toString()
                val location = binding.shopLocation.editText?.text.toString()
                val price = binding.priceInputLayout.editText?.text.toString()
                val postId = UUID.randomUUID().toString()
                val ownerId = FirebaseAuth.getInstance().currentUser?.uid.toString()
                val imageUrl = imageUri.toString()
                val post = Post(postId, ownerId, imageUrl, name, type, condition, desc, location, price)
                when {
                    imageUri == null -> {
                        Toast.makeText(
                            requireContext(),
                            "Please Select an image",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    name.isEmpty() -> {
                        binding.shopNam.error = "Shop name is required"
                        binding.shopNam.requestFocus()
                    }
                    type.isEmpty() -> {
                        binding.shopDesc.error = "Shop type is required"
                        binding.shopDesc.requestFocus()
                    }
                    condition.isEmpty() -> {
                        binding.shopWebsite.error = "Shop website is required"
                        binding.shopWebsite.requestFocus()
                    }
                    desc.isEmpty() -> {
                        binding.shopPhone.error = "Shop phone is required"
                        binding.shopPhone.requestFocus()
                    }
                    location.isEmpty() -> {
                        binding.shopLocation.error = "Shop location is required"
                        binding.shopLocation.requestFocus()
                    }
                    price.isEmpty() -> {
                        binding.priceInputLayout.error = "Price is required"
                        binding.priceInputLayout.requestFocus()
                    }
                    else -> {
                        if(CheckInternet.isConnected(requireContext())){
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.postAd(post, imageUri!!)
                            }
                            viewModel.postAd.observe(viewLifecycleOwner){
                                when(it){
                                    is Resource.Loading -> {
                                        binding.createShopProgress.isVisible = true
                                        binding.btnPostAd.text = "Posting..."
                                    }
                                    is Resource.Error -> {
                                        binding.createShopProgress.isVisible = false
                                        binding.btnPostAd.text = "Post Ad"
                                        Toast.makeText(requireContext(), it.string, Toast.LENGTH_SHORT).show()
                                    }

                                    is Resource.Success -> {
                                        binding.createShopProgress.isVisible = false
                                        binding.btnPostAd.text = "Post Ad"
                                        Toast.makeText(requireContext(), "Ad posted successfully", Toast.LENGTH_SHORT).show()
                                        requireActivity().onBackPressed()
                                    }
                                }
                            }
                        }
                        else{
                            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }


        return view
    }

    private fun openGallery(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == IMAGE_PICK_REQUEST && resultCode == Activity.RESULT_OK -> {
                imageUri = data?.data
                Glide.with(this@ShopsAddPostFragment)
                    .load(imageUri)
                    .circleCrop()
                    .into(binding.logoImageView)
                binding.addLogo.isVisible = false
            }
        }
    }
}