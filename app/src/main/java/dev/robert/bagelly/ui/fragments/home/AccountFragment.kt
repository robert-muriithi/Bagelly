package dev.robert.bagelly.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentAccountBinding
import dev.robert.bagelly.ui.fragments.account.viewmodel.MyAccountViewModel
import dev.robert.bagelly.ui.fragments.home.viewmodel.HomeViewModel
import dev.robert.bagelly.utils.Resource

@AndroidEntryPoint
class AccountFragment : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentAccountBinding
    private val myAccountViewModel : MyAccountViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        val view = binding.root
        fetchAccountDetails()


        return view
    }

    private fun fetchAccountDetails() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                myAccountViewModel.getSingleUser(user.uid)
            }
        }
        myAccountViewModel.user.observe(viewLifecycleOwner) {
            when(it){
                is Resource.Success -> {
                    binding.userName.text = it.data.name
                    binding.userEmail.text = it.data.email
                    binding.textView27.text = it.data.phoneNumber
                    Glide.with(requireContext()).load(it.data.profileImageUrl).into(binding.circleImageView3)
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }

}