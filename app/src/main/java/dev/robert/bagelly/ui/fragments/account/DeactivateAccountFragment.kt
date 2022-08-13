package dev.robert.bagelly.ui.fragments.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentDeactivateAccountBinding
@AndroidEntryPoint
class DeactivateAccountFragment : Fragment() {
    private lateinit var binding: FragmentDeactivateAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDeactivateAccountBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as AppCompatActivity).setSupportActionBar(binding.deactivateAccToolbar)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        binding.deactivateAccButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
                .setTitle("Are you sure?")
                .setMessage("This will deactivate your account and you will no longer be able to log in.")
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton("Yes"){ _, i ->
                    Toast.makeText(requireContext(), "Yes", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No"){ _, i ->
                    Toast.makeText(requireContext(), "No", Toast.LENGTH_SHORT).show()
                }.create()
            alertDialog.show()
        }


        return view
    }

}