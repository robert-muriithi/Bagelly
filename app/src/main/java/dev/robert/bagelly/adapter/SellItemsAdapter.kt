package dev.robert.bagelly.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.robert.bagelly.databinding.RecentlyUploadedItemLayoutBinding
import dev.robert.bagelly.model.Sell

class SellItemsAdapter : ListAdapter<Sell, SellItemsAdapter.SellViewHolder>(SellDiffUtil) {
    class SellViewHolder(private val binding: RecentlyUploadedItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Sell?) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellViewHolder {
        return SellViewHolder(
            RecentlyUploadedItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: SellViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    object SellDiffUtil : DiffUtil.ItemCallback<Sell>() {
        override fun areItemsTheSame(oldItem: Sell, newItem: Sell): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Sell, newItem: Sell): Boolean {
            return oldItem == newItem
        }
    }

}
