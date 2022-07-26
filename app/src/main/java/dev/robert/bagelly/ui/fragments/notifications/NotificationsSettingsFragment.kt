package dev.robert.bagelly.ui.fragments.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.databinding.FragmentNotificationsSettingsBinding

@AndroidEntryPoint
class NotificationsSettingsFragment : Fragment() {
    private lateinit var binding: FragmentNotificationsSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as AppCompatActivity).setSupportActionBar(binding.notificationsSettingsFragmentToolbar)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)


        return view
    }

}