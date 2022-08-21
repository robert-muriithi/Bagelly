package dev.robert.bagelly.ui.fragments.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.kanyideveloper.kenyan_counties.Kenya
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentMyAccountBinding
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.ui.fragments.account.viewmodel.MyAccountViewModel
import dev.robert.bagelly.utils.CheckInternet
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

@AndroidEntryPoint
class MyAccountFragment : Fragment() {
    @Inject lateinit var preferences: SharedPreferences
    private lateinit var binding: FragmentMyAccountBinding
    private val viewModel : MyAccountViewModel by viewModels()
    private var imageUri : Uri? = null
    private var IMAGE_PICK_CODE = 1000
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.myAccountFragmentToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.drop_down_item, Kenya.counties())
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.locationSpinner.adapter = arrayAdapter

        loadAccountDetails()

        binding.editIcon.setOnClickListener {
            openFileChooser()
        }


        return view
    }

    private fun loadAccountDetails() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getSingleUser(userId!!)
        }
        viewModel.user.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Loading -> {
                    binding.progressIndicator.isVisible = true
                    binding.progressIndicator.show()
                    hideEditText()
                }
                is Resource.Error -> {
                    binding.progressIndicator.isVisible = false
                    binding.progressIndicator.hide()
                    showEditText()
                    Toast.makeText(requireContext(), it.string, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    binding.progressIndicator.isVisible = false
                    binding.progressIndicator.hide()
                    showEditText()
                    binding.nameLayout.editText?.setText(it.data.name)
                    binding.lNameInputLayout.editText?.setText(it.data.email)
                    binding.phoneNumberLayout.editText?.setText(it.data.phoneNumber)

                    //binding.locationSpinner.setSelection(it.l ocation)
                    Glide.with(requireContext())
                        .load(it.data.profileImageUrl)
                        .into(binding.circleImageView2)
                }
            }
        }
    }

    private fun openFileChooser() {
        ImagePicker.with(this)
            .galleryOnly()
            .crop(1f, 1f)                    //Crop image(Optional)
            .compress(1024)                  //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)      //Final image resolution will be less than 1080 x 1080(Optional)
            .start(IMAGE_PICK_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK -> {
                imageUri = data?.data
                Glide.with(this)
                    .load(imageUri)
                    .circleCrop()
                    .into(binding.circleImageView2)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editProfile -> {
                updateUserDetails()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUserDetails() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val name = binding.name.text.toString()
        val email = binding.email.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()
        val location = binding.locationSpinner.selectedItem.toString()
        val profileImageUrl = imageUri.toString()
        val profileUri = imageUri
        val user = Users(userId!!, name, email, phoneNumber, profileImageUrl, location)

        FirebaseAuth.getInstance().currentUser?.updateEmail(email)
        if (CheckInternet.isConnected(requireContext())){
            if (imageUri != null){
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.updateUser(userId, profileUri!!, user)
                }
                viewModel.updateUser.observe(viewLifecycleOwner) {
                    when(it) {
                        is Resource.Loading -> {
                            binding.progressIndicator.isVisible = true
                            binding.progressIndicator.show()
                            hideEditText()
                            Toast.makeText(requireContext(), "Updating Details", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Error -> {
                            binding.progressIndicator.isVisible = false
                            binding.progressIndicator.hide()
                            showEditText()
                            Toast.makeText(requireContext(), it.string, Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Success -> {
                            binding.progressIndicator.isVisible = false
                            binding.progressIndicator.hide()
                            showEditText()
                            updateSharedPrefs()
                            Toast.makeText(requireContext(), "Profile updated", Toast.LENGTH_SHORT).show()
                            requireActivity().onBackPressed()
                        }
                    }
                }
            }
            else{
                Toast.makeText(requireContext(), "Pick an image", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideEditText(){
        binding.nameLayout.editText?.isEnabled = false
        binding.lNameInputLayout.editText?.isEnabled = false
        binding.phoneNumberLayout.editText?.isEnabled = false
        binding.locationSpinner.isEnabled = false
    }
    private fun showEditText(){
        binding.nameLayout.editText?.isEnabled = true
        binding.lNameInputLayout.editText?.isEnabled = true
        binding.phoneNumberLayout.editText?.isEnabled = true
        binding.locationSpinner.isEnabled = true
    }
    private fun updateSharedPrefs() : Boolean{
        val editor = preferences.edit()
        editor.putString("user_id", FirebaseAuth.getInstance().currentUser?.uid.toString())
        editor.putString("user_name", binding.name.text.toString())
        editor.putString("user_email", binding.email.text.toString())
        editor.putString("user_phone", binding.phoneNumber.text.toString())
        editor.putString("user_image", imageUri.toString())
        editor.putString("user_loc", binding.locationSpinner.selectedItem.toString())
        editor.apply()
        return true
    }
}