package dev.robert.bagelly.ui.fragments.sell

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentSellItemDetailsBinding
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.ui.fragments.sell.viewmodel.SellViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SellItemDetailsFragment : Fragment() {
    private lateinit var binding: FragmentSellItemDetailsBinding
    private val args by navArgs<SellItemDetailsFragmentArgs>()
    private val viewModel : SellViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSellItemDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.itemViewToolbar)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = args.itemDetails.itemName
        setHasOptionsMenu(true)
        loadData()

        binding.apply {
            btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel: 0700123456")
                startActivity(intent)
            }
            btnWhatsapp.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://api.whatsapp.com/send?phone=+254700123456")
                startActivity(intent)
            }
        }


        return view
    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {
        val itemImage = args.itemDetails.image1
        val itemName = args.itemDetails.itemName
        val condition = args.itemDetails.condition
        val price = args.itemDetails.price
        val location = args.itemDetails.location
        val description = args.itemDetails.description

        Glide.with(requireContext())
            .load(itemImage)
            .fitCenter()
            .into(binding.postImage)
        binding.tvPostTitle.text = itemName
        binding.tvPostCondition.text = condition
        binding.tvPostPrice.text = "Ksh: $price"
        binding.tvPostLocation.text = location
        binding.tvPostDescription.text = description
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val currentUID = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUID == args.itemDetails.sellerId) {
            inflater.inflate(R.menu.view_item_menu_when_logged, menu)
        }
        else{
            inflater.inflate(R.menu.view_item_menu, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete -> {
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.setTitle("Delete Item")
                alertDialog.setMessage("Are you sure you want to delete this item?")
                alertDialog.setPositiveButton("Yes"){ _, _ ->
                    val sell = args.itemDetails
                    viewModel.deleteItem(sell)
                    Toast.makeText(requireContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                }
                alertDialog.setNegativeButton("No"){ _, _ ->
                    // do nothing
                }
                alertDialog.show()
            }
            R.id.action_share -> {
                // share item
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "Check out this item on Bagelly: ${args.itemDetails.itemName}")
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}