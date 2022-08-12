package dev.robert.bagelly.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.RecentlyUploadedItemLayoutBinding
import dev.robert.bagelly.model.Sell


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
        holder.itemView.setOnClickListener {

        }
        holder.bind(sell)
    }
}
