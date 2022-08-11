package dev.robert.bagelly.ui.fragments.shops

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentViewShopBinding
import dev.robert.bagelly.ui.fragments.shops.viewmodel.ShopsViewModel

@AndroidEntryPoint
class ViewShopFragment : Fragment() {
    private lateinit var binding : FragmentViewShopBinding
    private val viewModel : ShopsViewModel by viewModels()
    private val args : ViewShopFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewShopBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.viewShopToolbar)
        (activity as AppCompatActivity).supportActionBar?.title = args.shopDetails.shopName
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        val view = binding.root

        setViews()


        return view
    }

    private fun setViews() {
        Glide
            .with(this@ViewShopFragment)
            .load(args.shopDetails.shopImage)
            .error(R.drawable.ic_error_placeholder)
            .into(binding.viewShopImage)
        binding.viewShopName.text = args.shopDetails.shopName
        binding.viewShopWebsite.text = args.shopDetails.shopWebsite
        binding.shopDescriptionTv.text = args.shopDetails.shopDescription
        if (FirebaseAuth.getInstance().currentUser?.uid == args.shopDetails.ownerId) {
            binding.editShopImage.visibility = View.VISIBLE
            binding.messageBtn.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shop_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.shareShop -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "https://bagelly.page.link/shop/${args.shopDetails.shopId}")
                startActivity(Intent.createChooser(intent, "Share via"))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}