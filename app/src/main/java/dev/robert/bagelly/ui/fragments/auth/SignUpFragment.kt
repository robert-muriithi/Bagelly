package dev.robert.bagelly.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.databinding.FragmentSignUpBinding
import dev.robert.bagelly.ui.fragments.auth.SignInFragment

@AndroidEntryPoint
class SignUpFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.loginTv.setOnClickListener {
            SignInFragment().show(parentFragmentManager, "signIn")
        }




        return view
    }

}