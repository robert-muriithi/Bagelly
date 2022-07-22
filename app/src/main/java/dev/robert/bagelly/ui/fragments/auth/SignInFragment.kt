package dev.robert.bagelly.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.databinding.FragmentSignInBinding

@AndroidEntryPoint
class SignInFragment : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()
        binding.registerTv.setOnClickListener {
            SignUpFragment().show(parentFragmentManager, "signUp")
        }

        binding.btnLogin.setOnClickListener {
            Toast.makeText(requireContext(), "Login", Toast.LENGTH_SHORT).show()
        }




        return view
    }

}