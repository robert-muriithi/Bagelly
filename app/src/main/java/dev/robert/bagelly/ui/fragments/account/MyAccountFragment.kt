package dev.robert.bagelly.ui.fragments.account

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.kenyan_counties.Kenya
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

        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.drop_down_item, Kenya.counties())
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.locationSpinner.adapter = arrayAdapter


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editProfile -> {
                Toast.makeText(requireContext(), "Save", Toast.LENGTH_SHORT).show()
                /*val action = MyAccountFragmentDirections.actionMyAccountFragmentToEditProfileFragment()
                Navigation.findNavController(binding.root).navigate(action)*/
            }
        }
        return super.onOptionsItemSelected(item)
    }
}