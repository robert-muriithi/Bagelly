package dev.robert.bagelly.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dev.robert.bagelly.R
import dev.robert.bagelly.data.repository.MainRepository
import dev.robert.bagelly.databinding.RecentlyUploadedItemLayoutBinding
import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.utils.Resource
import javax.inject.Inject


class RecentUploadsAdapter : ListAdapter<Sell, RecentUploadsAdapter.ShopsViewHolder>(ShopsComparator) {
    @Inject lateinit var repository : MainRepository
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
        val fav = holder.itemView.findViewById<ImageView>(R.id.favIcon)
        /*var counter = 0
        while (counter < repository.favourites.size){
            if (sell?.itemUniqueId == repository.favourites[counter].itemUniqueId){
                fav.setImageResource(R.drawable.ic_fav_filled)
                break
            }else{
                fav.setImageResource(R.drawable.ic_fav_empty)
            }
            counter++
        }*/

        fav.setOnClickListener {
            fav.setImageResource(R.drawable.ic_favorite_filled)

        }
        holder.bind(sell)
    }
}
