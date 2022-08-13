package dev.robert.bagelly.ui.fragments.search

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.searchFragmentToolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "Search"
        //(activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val view = binding.root
        binding.searchFragmentTextInputLayout.focusable = View.FOCUSABLE_AUTO


        return view
    }



}