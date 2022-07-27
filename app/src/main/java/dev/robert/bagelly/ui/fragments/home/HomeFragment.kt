package dev.robert.bagelly.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentHomeBinding
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).supportActionBar?.hide()
        (activity as AppCompatActivity).setSupportActionBar(binding.homeToolbar)

        binding.apply {
            accountImage.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
            }
            searchButton.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            }
            imageView2.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_notificationsFragment)
            }
            agricultureCardView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_agricultureAndFoodsFragment)
            }
            animalsCardView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_animalsAndPetsFragment)
            }
            furnitureCardView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_homeAndFurnitureFragment)
            }
            electronicsCardView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_electronicsFragment)
            }
            vehiclesCardView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_vehiclesFragment)
            }
            healthAndBeautyCardView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_healthAndBeautyFragment)
            }
            fashionCardView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_fashionFragment)
            }
            phonesCardView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_phonesAndTabletsFragment)
            }
            propertyCardView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_propertyFragment)
            }
            servicesCardView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_servicesFragment)
            }
        }

        return view
    }

    override fun onResume() {
        (activity as AppCompatActivity).setSupportActionBar(binding.homeToolbar)
        super.onResume()
    }

}