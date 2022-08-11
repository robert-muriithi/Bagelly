package dev.robert.bagelly.ui.fragments.shops

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentViewPostBinding

@AndroidEntryPoint
class ViewPostFragment : Fragment() {
    private lateinit var binding: FragmentViewPostBinding
    private val args by navArgs<ViewPostFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewPostBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.postsToolbar)
        (activity as AppCompatActivity).supportActionBar?.title = args.postDetails.postName
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadDetails()


        return view
    }

    private fun loadDetails() {
        Glide.with(this)
            .load(args.postDetails.postImage)
            .into(binding.postImage)
    }

}