package dev.robert.bagelly.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.ExclusiveShopsItemLayoutBinding
import dev.robert.bagelly.model.Shop

class FashionShopsAdapter : ListAdapter<Shop, FashionShopsAdapter.ShopsViewHolder>(ShopsComparator) {
    class ShopsViewHolder(private val binding: ExclusiveShopsItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(shop: Shop?){
            binding.shopName.text = shop?.shopName
            binding.locationTv.text = shop?.shopLocation
            Glide.with(binding.root.context)
                .load(shop?.shopImage)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.shopIconProgressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.shopIconProgressBar.isVisible = false
                        return false
                    }
                })
                .error(R.drawable.ic_error_placeholder)
                .centerCrop()
                .into(binding.shopIcon)
        }
    }

    object ShopsComparator : DiffUtil.ItemCallback<Shop>() {
        override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean {
            return oldItem.shopId == newItem.shopId
        }

        override fun areContentsTheSame(oldItem: Shop, newItem: Shop): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopsViewHolder {
        return ShopsViewHolder(ExclusiveShopsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ShopsViewHolder, position: Int) {
        val sell = getItem(position)
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, "${sell.shopName}", Toast.LENGTH_SHORT).show()
        }
        holder.bind(sell)
    }
}