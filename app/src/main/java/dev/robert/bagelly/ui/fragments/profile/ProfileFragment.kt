package dev.robert.bagelly.ui.fragments.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as AppCompatActivity).setSupportActionBar(binding.profileFragmentToolbar)
        //(activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Profile"

        binding.apply {
            manageAccountLayout.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_myAccountFragment)
            }
            settingLayout.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
            }
            feedbackLayout.setOnClickListener {
                //start gmail
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:" + "robertmuriithi390@gmail.com" ) // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(intent)
                }
            }
            notificationsLayout.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_notificationsSettingsFragment)
            }
            detailsLayout.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_deactivateAccountFragment)
            }
        }

        return view
    }

}