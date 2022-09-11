package dev.robert.bagelly.ui.fragments.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentRecommendedPostsBinding

@AndroidEntryPoint
class RecommendedPostsFragment : Fragment() {
   private lateinit var binding: FragmentRecommendedPostsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecommendedPostsBinding.inflate(inflater, container, false)
        val view = binding.root



        return view
    }

}