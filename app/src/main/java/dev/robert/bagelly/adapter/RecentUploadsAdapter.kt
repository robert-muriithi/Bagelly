package dev.robert.bagelly.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dev.robert.bagelly.R
import dev.robert.bagelly.data.repository.MainRepository
import dev.robert.bagelly.data.repository.MainRepositoryImpl
import dev.robert.bagelly.databinding.RecentlyUploadedItemLayoutBinding
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.ui.fragments.home.HomeFragmentDirections
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject


class RecentUploadsAdapter : ListAdapter<Sell, RecentUploadsAdapter.ShopsViewHolder>(ShopsComparator) {

    class ShopsViewHolder(private val binding: RecentlyUploadedItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(sell: Sell?){
            binding.itemName.text = sell?.itemName
            binding.recentTv.text = "Recent"
            binding.sellPrice.text = "Ksh "+sell?.price
            binding.sellLocation.text = sell?.location

            Glide.with(binding.root.context)
                .load(sell?.images?.get(0))
                .placeholder(R.drawable.avatar)
                .into(binding.itemImage)

            Glide.with(binding.root.context)
                .load(sell?.images?.get(0))
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {

                        return false
                    }
                })
                .error(R.drawable.ic_error_placeholder)
                .centerCrop()
                .into(binding.itemImage)
        }
    }

    object ShopsComparator : DiffUtil.ItemCallback<Sell>() {
        override fun areItemsTheSame(oldItem: Sell, newItem: Sell): Boolean {
            return oldItem.itemUniqueId == newItem.itemUniqueId
        }

        override fun areContentsTheSame(oldItem: Sell, newItem: Sell): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopsViewHolder {
        return ShopsViewHolder(RecentlyUploadedItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ShopsViewHolder, position: Int) {
        val sell = getItem(position)
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val storageReference = FirebaseStorage.getInstance().reference
        val checkBox = holder.itemView.findViewById<CheckBox>(R.id.favIcon)
        val itemId = sell.itemUniqueId
        MainRepositoryImpl.getInstance(db, storageReference).isItemFavourite(itemId) {
            when (it) {
                is Resource.Success -> {
                    if (it.data) {
                        checkBox.isChecked = true
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(holder.itemView.context, it.string, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        checkBox.setOnClickListener {
            val isChecked = checkBox.isChecked
            if (isChecked) {
                MainRepositoryImpl.getInstance(db, storageReference).addToFavourite(sell) {
                    when (it) {
                        is Resource.Success -> {
                            Toast.makeText(holder.itemView.context, "Added to favourites", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Error -> {
                            Toast.makeText(holder.itemView.context, it.string, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(
                                holder.itemView.context,
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                MainRepositoryImpl.getInstance(db, storageReference).removeFromFavourite(sell) {
                    when (it) {
                        is Resource.Success -> {
                            Toast.makeText(holder.itemView.context, "Removed from favourites", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Error -> {
                            Toast.makeText(holder.itemView.context, it.string, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(
                                holder.itemView.context,
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            /*checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                MainRepositoryImpl.getInstance(db, storageReference).addToFavourite(sell){
                    when(it){
                        is Resource.Success -> {
                            it.data.let {
                                Toast.makeText(buttonView.context, "Added to favourites", Toast.LENGTH_SHORT).show()
                            }
                        }
                        is Resource.Error -> {
                            Toast.makeText(buttonView.context, it.string, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(buttonView.context, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            } else {
                MainRepositoryImpl.getInstance(db, storageReference).removeFromFavourite(sell) {
                    when (it) {
                        is Resource.Success -> {
                            Toast.makeText(
                                buttonView.context,
                                "Removed from favourites",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Error -> {
                            Toast.makeText(buttonView.context, it.string, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(buttonView.context, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }*/

        }
        val card = holder.itemView.findViewById<MaterialCardView>(R.id.itemCardView)
        card.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSellItemDetailsFragment(sell)
            Navigation.findNavController(holder.itemView).navigate(action)
        }
        holder.bind(sell)
    }

}
