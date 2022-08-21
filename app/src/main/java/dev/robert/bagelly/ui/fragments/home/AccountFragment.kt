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
import com.google.firebase.firestore.auth.User
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
        initViews()

        return view
    }

    private fun initViews() {
        binding.userName.text = requireContext().getSharedPreferences("bagelly", 0).getString("user_name", "")
        binding.userEmail.text = requireContext().getSharedPreferences("bagelly", 0).getString("user_email", "")
        binding.textView27.text = requireContext().getSharedPreferences("bagelly", 0).getString("user_phone", "")
        Glide.with(requireContext())
            .load(requireContext().getSharedPreferences("bagelly", 0).getString("user_image", ""))
            .placeholder(R.drawable.avatar)
            .into(binding.circleImageView3)
    }


}