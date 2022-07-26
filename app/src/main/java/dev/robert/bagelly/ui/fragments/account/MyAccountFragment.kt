package dev.robert.bagelly.ui.fragments.account

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentMyAccountBinding

@AndroidEntryPoint
class MyAccountFragment : Fragment() {
    private lateinit var binding: FragmentMyAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.myAccountFragmentToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editProfile -> {
                findNavController().navigate(R.id.action_myAccountFragment_to_editProfileFragment)
                /*val action = MyAccountFragmentDirections.actionMyAccountFragmentToEditProfileFragment()
                Navigation.findNavController(binding.root).navigate(action)*/
            }
        }
        return super.onOptionsItemSelected(item)
    }
}