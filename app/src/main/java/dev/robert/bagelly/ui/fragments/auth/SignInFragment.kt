package dev.robert.bagelly.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.databinding.FragmentSignInBinding
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.ui.fragments.auth.viewmodel.AuthViewModel
import dev.robert.bagelly.utils.CheckInternet
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentSignInBinding
    private val viewModel : AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.apply {
            registerTv.setOnClickListener {
                SignUpFragment().show(parentFragmentManager, "signUp")
            }
            btnLogin.setOnClickListener {
                val email = binding.emailTinputLayout.editText?.text.toString().trim()
                val password = binding.passwordInputLayout.editText?.text.toString().trim()
                when{
                    email.isEmpty() ->{
                        binding.emailTinputLayout.error = "Email is required"
                        binding.emailTinputLayout.isErrorEnabled = true
                    }
                    password.isEmpty() -> {
                        binding.passwordInputLayout.error = "Password is required"
                        binding.passwordInputLayout.isErrorEnabled = true
                    }
                    else ->{
                        binding.passwordInputLayout.isErrorEnabled = false
                        binding.emailTinputLayout.isErrorEnabled = false

                        if (CheckInternet.isConnected(requireContext())){
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.login(email, password)
                            }

                            viewModel.login.observe(viewLifecycleOwner){
                                when(it){
                                    is Resource.Loading -> {
                                        binding.progressBar3.isVisible = true
                                    }

                                    is Resource.Success -> {
                                        binding.progressBar3.isVisible = false
                                        Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                                        dismiss()
                                    }

                                    is Resource.Error -> {
                                        binding.progressBar3.isVisible = false
                                        Toast.makeText(requireContext(), it.string, Toast.LENGTH_SHORT).show()
                                    }

                                }
                            }
                        }
                    }
                }

            }
        }

        return view
    }

}