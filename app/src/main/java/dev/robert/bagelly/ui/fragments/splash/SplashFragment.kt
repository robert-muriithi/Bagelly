package dev.robert.bagelly.ui.fragments.splash

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentSplashBinding
@AndroidEntryPoint
class SplashFragment  : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }, 3000)

        return view
    }

}