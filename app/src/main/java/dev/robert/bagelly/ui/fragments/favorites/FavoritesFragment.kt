package dev.robert.bagelly.ui.fragments.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.adapter.FavouriteItemsAdapter
import dev.robert.bagelly.databinding.FragmentFavoritesBinding
import dev.robert.bagelly.ui.activity.MainActivity
import dev.robert.bagelly.ui.fragments.favorites.viewmodel.FavoritesViewModel
import dev.robert.bagelly.utils.Resource

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding : FragmentFavoritesBinding
    private val viewModel : FavoritesViewModel by viewModels()
    private val adapter by lazy { FavouriteItemsAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.favoritesFragmentToolbar)
        setHasOptionsMenu(true)


        binding.favoritesRecyclerView.adapter = adapter
        fetchFavourites()
        observeFavourites()

        return view
    }

    private fun observeFavourites() {
        viewModel.favoriteItem.observe(viewLifecycleOwner) {
            when(it){
                is Resource.Loading -> {
                    isLoading(true)
                }
                is Resource.Success -> {
                    isLoading(false)
                    adapter.submitList(it.data)
                }
                is Resource.Error -> {
                    isLoading(false)
                }
            }
        }
    }

    private fun isLoading(isLoading: Boolean){
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isLoading(isLoading)
        }
        if(isLoading){
            viewModel.isLoading.observe(viewLifecycleOwner){
                if(it){
                    startShimmer()
                    //binding.progressBar.isVisible = true
                }
                else{
                    stopShimmer()
                    //binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun fetchFavourites() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
             viewModel.getFavoriteItem()
        }
    }

    private fun stopShimmer() {

    }

    private fun startShimmer() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteAll -> {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Delete All")
                dialog.setMessage("Are you sure you want to delete all favorites?")
                dialog.setPositiveButton("Yes"){ _, _ ->
                    Toast.makeText(requireContext(), "Favorites cleared", Toast.LENGTH_SHORT).show()
                    viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                        viewModel.deleteAllItems()
                    }
                    adapter.submitList(null)
                }
                dialog.setNegativeButton("No"){ _, _ ->
                    dialog.setCancelable(true)
                }
                dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}