package dev.robert.bagelly.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.adapter.RecentUploadsAdapter
import dev.robert.bagelly.databinding.FragmentHomeBinding
import dev.robert.bagelly.ui.fragments.home.viewmodel.HomeViewModel
import dev.robert.bagelly.utils.Resource

@AndroidEntryPoint
class HomeFragment  : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth  : FirebaseAuth
    private val viewModel : HomeViewModel by viewModels()
    private val adapter by lazy { RecentUploadsAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).supportActionBar?.hide()
        (activity as AppCompatActivity).setSupportActionBar(binding.homeToolbar)
        binding.recentlyUploadedItemsRecyclerView.adapter = adapter
        //fetchRecentUploads()
        fetchRecentUploads()
        observeViewModel()

        auth = FirebaseAuth.getInstance()

        binding.apply {

            accountImage.setOnClickListener {
                if (auth.currentUser != null && auth.currentUser?.isEmailVerified!!) {
                    findNavController().navigate(R.id.action_homeFragment_to_accountFragment)
                }
                else{
                    findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
                }
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

    private fun observeViewModel() {
        viewModel.recentSell.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    hideViews()
                    binding.progressBar.isVisible = true
                }
                is Resource.Error -> {
                    showViews()
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.string, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    showViews()
                    binding.progressBar.isVisible = false
                    adapter.submitList(it.data)
                }
            }
        }
    }

    private fun fetchRecentUploads() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getRecentSells()
        }
    }


    /*override fun onResume() {
        if (auth.currentUser != null && auth.currentUser?.isEmailVerified!!) {
            binding.accountImage.isVisible = true
        }
        else{
            binding.accountImage.isVisible = false
        }
    super.onResume()
    }*/
    private fun hideViews(){
        binding.textView9.isVisible = false
        binding.textView7.isVisible = false
        binding.imageView12.isVisible = false
        binding.textView12.isVisible = false
        binding.textView13.isVisible = false
        binding.imageView13.isVisible = false
        binding.tv14.isVisible = false
        binding.textView6.isVisible = false
        binding.imageView15.isVisible = false
    }
    private fun showViews(){
        binding.textView9.isVisible = true
        binding.textView7.isVisible = true
        binding.imageView12.isVisible = true
        binding.textView12.isVisible = true
        binding.textView13.isVisible = true
        binding.imageView13.isVisible = true
        binding.tv14.isVisible = true
        binding.textView6.isVisible = true
        binding.imageView15.isVisible = true
    }


}