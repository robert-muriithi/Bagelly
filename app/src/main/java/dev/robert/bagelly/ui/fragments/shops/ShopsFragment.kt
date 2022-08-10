package dev.robert.bagelly.ui.fragments.shops

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.adapter.*
import dev.robert.bagelly.databinding.FragmentShopsBinding
import dev.robert.bagelly.ui.fragments.shops.viewmodel.ShopsViewModel
import dev.robert.bagelly.utils.Resource
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShopsFragment : Fragment() {
    private lateinit var binding: FragmentShopsBinding
    private val viewModel: ShopsViewModel by viewModels()
    private val electronicShopsAdapter by lazy { ElectronicShopsAdapter() }
    private val homeAndLivingShopAdapter by lazy { HomeAndLivingShopAdapter() }
    private val mobilePhoneShopsAdapter by lazy { MobilePhoneShopsAdapter() }
    private val fashionShopsAdapter by lazy { FashionShopsAdapter() }
    private val motorShopsAdapter by lazy { MotorShopsAdapter() }
    private val otherStoresAdapter by lazy { OtherStoresAdapter() }
    private val generalStoresAdapter by lazy { GeneralStoresAdapter() }
    private val serviceProvidersAdapter by lazy { ServiceProvidersAdapter() }
    private val farmInputStoresAdapter by lazy { FarmInputStoresAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShopsBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).supportActionBar?.hide()
        (activity as AppCompatActivity).setSupportActionBar(binding.shopsFragmentToolbar)

        binding.addStoreFab.setOnClickListener {
            findNavController().navigate(R.id.action_shopsFragment_to_createShopFragment)
        }
        initRecyclerView()
        observeShops()
        fetchShops()



        return view
    }

    private fun startShimmer(){
        binding.shimmerViewContainer.isVisible = true
        binding.shimmerViewContainer.startShimmer()
    }
    private fun stopShimmer(){
        binding.shimmerViewContainer.isVisible = false
        binding.shimmerViewContainer.stopShimmer()
    }
    private fun initRecyclerView() {
        binding.electronicShopsRecyclerView.adapter = electronicShopsAdapter
        binding.homeAndLivingStoresRecyclerView.adapter = homeAndLivingShopAdapter
        binding.recyclerView.adapter = mobilePhoneShopsAdapter
        binding.fashionStoresRecyclerView.adapter = fashionShopsAdapter
        binding.motorsRecyclerView.adapter = motorShopsAdapter
        binding.generalStoresRecyclerView.adapter = generalStoresAdapter
        binding.serviceProvidersRecyclerView.adapter = serviceProvidersAdapter
        binding.otherStoresRecyclerView.adapter = otherStoresAdapter
        binding.farmInputsStoresRecyclerView.adapter = farmInputStoresAdapter
    }

    private fun observeShops() {
        viewModel.getElectronicShops.observe(viewLifecycleOwner){ result ->
            when(result){
                is Resource.Loading -> {
                    hideViews()
                    startShimmer()
                }
                is Resource.Success -> {
                    //binding.shopsProgress.isVisible = false
                    electronicShopsAdapter.submitList(result.data)
                    showViews()
                    stopShimmer()
                }
                is Resource.Error -> {
                    //binding.shopsProgress.isVisible = false
                    hideViews()
                    stopShimmer()
                    Toast.makeText(requireContext(), "${result.copy()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getHomeAndLivingStores.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    //binding.shopsProgress.isVisible = true
                }
                is Resource.Success -> {
                    //binding.shopsProgress.isVisible = false
                    homeAndLivingShopAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    //binding.shopsProgress.isVisible = false
                    Toast.makeText(requireContext(), "${it.copy()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getMobilePhoneShops.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    //binding.shopsProgress.isVisible = true
                }
                is Resource.Success -> {
                    //binding.shopsProgress.isVisible = false
                    mobilePhoneShopsAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    //binding.shopsProgress.isVisible = false
                    Toast.makeText(requireContext(), "${it.copy()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getFashionShops.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    //binding.shopsProgress.isVisible = true
                }
                is Resource.Success -> {
                    //binding.shopsProgress.isVisible = false
                    fashionShopsAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    //binding.shopsProgress.isVisible = false
                    Toast.makeText(requireContext(), "${it.copy()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getMotorsDealers.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    //binding.shopsProgress.isVisible = true
                }
                is Resource.Success -> {
                    //binding.shopsProgress.isVisible = false
                    motorShopsAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    //binding.shopsProgress.isVisible = false
                    Toast.makeText(requireContext(), "${it.copy()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getGeneralStores.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                   // binding.shopsProgress.isVisible = true
                }
                is Resource.Success -> {
                    //binding.shopsProgress.isVisible = false
                    generalStoresAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    //binding.shopsProgress.isVisible = false
                    Toast.makeText(requireContext(), "${it.copy()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getServiceProviders.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    //binding.shopsProgress.isVisible = true
                }
                is Resource.Success -> {
                    //binding.shopsProgress.isVisible = false
                    serviceProvidersAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    //binding.shopsProgress.isVisible = false
                    Toast.makeText(requireContext(), "${it.copy()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getFarmInputsStores.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    //binding.shopsProgress.isVisible = true
                }
                is Resource.Success -> {
                    //binding.shopsProgress.isVisible = false
                    farmInputStoresAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                   // binding.shopsProgress.isVisible = false
                    Toast.makeText(requireContext(), "${it.copy()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getOtherStores.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    //binding.shopsProgress.isVisible = true
                }
                is Resource.Success -> {
                    //binding.shopsProgress.isVisible = false
                   otherStoresAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    //binding.shopsProgress.isVisible = false
                    Toast.makeText(requireContext(), "${it.copy()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getShops() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getElectronicStores()
        }
    }
    private fun getHomeAndLivingStores() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getHomeAndLivingStores()
        }
    }
    private fun getMobilePhoneShops() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMobilePhonesStores()
        }
    }
    private fun getFashionShops(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFashionStores()
        }
    }
    private fun getMotorsShops() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMotorsDealers()
        }
    }
    private fun getGeneralStores() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getGeneralStores()
        }
    }
    private fun getServiceProviders() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getServiceProviders()
        }
    }
    private fun getFarmInputStores() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFarmInputsStores()
        }
    }
    private fun getOtherStores() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getOtherStore()
        }
    }

    private fun hideViews(){
        binding.textView8.isVisible = false
        binding.homeAndLivingTv.isVisible = false
        binding.imageView17.isVisible = false
        binding.homeAndLivingTv.isVisible = false
        binding.imageView18.isVisible = false
        binding.viewAllHomeStores.isVisible = false
        binding.viewAllElectronicShops.isVisible = false
        binding.mobilePhonesTv.isVisible = false
        binding.fashionStoresTv.isVisible = false
        binding.motorsTv.isVisible = false
        binding.generalStoresTv.isVisible = false
        binding.serviceProvidersTv.isVisible = false
        binding.textView28.isVisible = false
        binding.otherStoresTv.isVisible = false

    }
    private fun showViews(){
        binding.textView8.isVisible = true
        binding.homeAndLivingTv.isVisible = true
        binding.imageView17.isVisible = true
        binding.homeAndLivingTv.isVisible = true
        binding.imageView18.isVisible = true
        binding.viewAllHomeStores.isVisible = true
        binding.viewAllElectronicShops.isVisible = true
        binding.mobilePhonesTv.isVisible = true
        binding.fashionStoresTv.isVisible = true
        binding.motorsTv.isVisible = true
        binding.generalStoresTv.isVisible = true
        binding.serviceProvidersTv.isVisible = true
        binding.textView28.isVisible = true
        binding.otherStoresTv.isVisible = true
    }
    private fun fetchShops(){
        getShops()
        getMobilePhoneShops()
        getHomeAndLivingStores()
        getFashionShops()
        getMotorsShops()
        getGeneralStores()
        getServiceProviders()
        getFarmInputStores()
        getOtherStores()
    }

}