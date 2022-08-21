package dev.robert.bagelly.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.Navigation
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
import dev.robert.bagelly.R
import dev.robert.bagelly.data.repository.MainRepository
import dev.robert.bagelly.data.repository.MainRepositoryImpl
import dev.robert.bagelly.databinding.FavoriteItemLayoutBinding
import dev.robert.bagelly.databinding.RecentlyUploadedItemLayoutBinding
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.ui.fragments.favorites.FavoritesFragmentDirections
import dev.robert.bagelly.ui.fragments.home.HomeFragmentDirections
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject

class FavouriteItemsAdapter : ListAdapter<Sell, FavouriteItemsAdapter.ShopsViewHolder>(ShopsComparator) {
    class ShopsViewHolder(private val binding: FavoriteItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        @Inject
        lateinit var repository : MainRepository
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
        return ShopsViewHolder(FavoriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: ShopsViewHolder, position: Int) {
        val sell = getItem(position)
        val deleteIcon = holder.itemView.findViewById<ImageView>(R.id.deleteIcon)
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val storageReference = FirebaseStorage.getInstance().reference
        val application = holder.itemView.context.applicationContext as Application
        deleteIcon.setOnClickListener {
            MainRepositoryImpl.getInstance(db, storageReference, application).removeFromFavourite(sell) {
                when (it) {
                    is Resource.Success -> {
                        notifyItemRemoved(position)
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
        val card = holder.itemView.findViewById<MaterialCardView>(R.id.favoriteCardView)
        card.setOnClickListener {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToSellItemDetailsFragment(sell)
            Navigation.findNavController(holder.itemView).navigate(action)
        }
        holder.bind(sell)
    }
}
