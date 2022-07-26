package dev.robert.bagelly.ui.fragments.notifications

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentNotificationsBinding
@AndroidEntryPoint
class NotificationsFragment : Fragment() {
    private lateinit var binding: FragmentNotificationsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.notificationsFragmentToolbar)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notifications_settings, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
             R.id.notificationSettings -> {
                findNavController().navigate(R.id.action_notificationsFragment_to_notificationsSettingsFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}