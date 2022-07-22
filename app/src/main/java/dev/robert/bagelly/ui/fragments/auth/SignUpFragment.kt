package dev.robert.bagelly.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
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
    private val id : String = ""
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

        binding.btnRegister.setOnClickListener {
            Toast.makeText(requireContext(), "Register", Toast.LENGTH_SHORT).show()
            val name = binding.nameInputLayout.editText?.text.toString()
            val email = binding.emailInputLayout.editText?.text.toString()
            val phoneNumber = binding.phoneNumberInputLayout.editText?.text.toString()
            val user = Users(id, name, email, phoneNumber)
            when{
                name.isEmpty() -> binding.nameInputLayout.error = "Name is required"
                email.isEmpty() -> binding.emailInputLayout.error = "Email is required"
                phoneNumber.isEmpty() -> binding.phoneNumberInputLayout.error = "Phone number is required"
                else -> {
                    if (CheckInternet.isConnected(requireContext())){
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.createUser(user)
                        }
                        viewModel.auth.observe(viewLifecycleOwner){ task ->
                            when(task){
                                is Resource.Loading -> {
                                    binding.progressBar2.visibility = View.VISIBLE
                                    binding.btnRegister.isEnabled = false
                                }
                                is Resource.Success -> {
                                    binding.progressBar2.visibility = View.GONE
                                    binding.btnRegister.isEnabled = true
                                    Snackbar.make(view, "User created successfully", Snackbar.LENGTH_LONG).show()
                                    dismiss()
                                }
                                is Resource.Error -> {
                                    binding.progressBar2.visibility = View.GONE
                                    binding.btnRegister.isEnabled = true
                                    Snackbar.make(view, task.string, Snackbar.LENGTH_LONG).show()
                                }
                            }
                        }

                    }
                    else {
                        Snackbar.make(requireView(), "Connect to internet and try again", Snackbar.LENGTH_LONG)
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