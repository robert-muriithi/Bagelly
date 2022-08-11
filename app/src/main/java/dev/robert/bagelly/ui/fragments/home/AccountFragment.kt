package dev.robert.bagelly.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentAccountBinding
import dev.robert.bagelly.ui.fragments.home.viewmodel.HomeViewModel

@AndroidEntryPoint
class AccountFragment : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentAccountBinding
    private val viewModel by viewModels<HomeViewModel>()
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
        binding.userEmail.text = FirebaseAuth.getInstance().currentUser?.email
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getUser()
        }
        viewModel.user.observe(viewLifecycleOwner) {
            binding.userName.text
        }
    }

}