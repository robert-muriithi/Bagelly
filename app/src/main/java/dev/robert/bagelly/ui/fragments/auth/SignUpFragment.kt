package dev.robert.bagelly.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.databinding.FragmentSignUpBinding
import dev.robert.bagelly.model.Users
import dev.robert.bagelly.ui.fragments.auth.viewmodel.AuthViewModel
import dev.robert.bagelly.utils.CheckInternet
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        //(activity as AppCompatActivity).supportActionBar?.hide()

        binding.loginTv.setOnClickListener {
            SignInFragment().show(parentFragmentManager, "signIn")
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.nameInputLayout.editText?.text.toString()
            val email = binding.emailInputLayout.editText?.text.toString()
            val phoneNumber = binding.phoneNumberInputLayout.editText?.text.toString()
            val password = binding.passInputLayout.editText?.text.toString().trim()
            val confirmPassword = binding.confPassInputLayout.editText?.text.toString().trim()


            when{
                name.isEmpty() -> {
                    binding.nameInputLayout.error = "Name is required"
                    binding.nameInputLayout.isErrorEnabled = true
                }
                email.isEmpty() -> {
                    binding.emailInputLayout.error = "Email is required"
                    binding.emailInputLayout.isErrorEnabled = true
                }
                phoneNumber.isEmpty() ->{
                    binding.phoneNumberInputLayout.error = "Phone number is required"
                    binding.phoneNumberInputLayout.isErrorEnabled = true
                }
                password.isEmpty() -> {
                    binding.passInputLayout.error = "Password is required"
                    binding.passInputLayout.isErrorEnabled = true
                }
                confirmPassword.isEmpty() -> {
                    binding.confPassInputLayout.error = "Confirm password is required"
                    binding.confPassInputLayout.isErrorEnabled = true
                }
                password != confirmPassword -> {
                    binding.passInputLayout.error = "Password does not match"
                    binding.confPassInputLayout.error = "Password does not match"
                    binding.passInputLayout.isErrorEnabled = true
                }
                else -> {
                    binding.nameInputLayout.isErrorEnabled = false
                    binding.emailInputLayout.isErrorEnabled = false
                    binding.phoneNumberInputLayout.isErrorEnabled = false
                    binding.passInputLayout.isErrorEnabled = false
                    binding.confPassInputLayout.isErrorEnabled = false
                    binding.btnRegister.isEnabled = false

                    if (CheckInternet.isConnected(requireContext())){
                        lifecycleScope.launch {
                            viewModel.register(name, email, phoneNumber, password)
                        }
                        viewModel.register.observe(viewLifecycleOwner){ authResult ->
                            when(authResult){
                                is Resource.Loading -> {
                                    binding.progressBar2.isVisible = true
                                    binding.btnRegister.isEnabled = false
                                }
                                is Resource.Success -> {
                                    binding.progressBar2.isVisible = false
                                    binding.btnRegister.isEnabled = true
                                    Snackbar.make(view, "Registration successful", Snackbar.LENGTH_LONG).show()
                                    dismiss()
                                }
                                is Resource.Error -> {
                                    binding.progressBar2.isVisible = false
                                    binding.btnRegister.isEnabled = true
                                    Snackbar.make(view, authResult.string, Snackbar.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                    else {
                        Snackbar.make(view, "No internet connection", Snackbar.LENGTH_LONG).show()
                    }

                }
            }
        }

        return view
    }

}