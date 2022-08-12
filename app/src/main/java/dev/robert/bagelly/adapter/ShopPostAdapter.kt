package dev.robert.bagelly.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.robert.bagelly.databinding.PostItemLayoutBinding
import dev.robert.bagelly.model.Post
import dev.robert.bagelly.ui.fragments.shops.ViewShopFragmentDirections

class ShopPostAdapter :  ListAdapter<Post, ShopPostAdapter.PostViewHolder>(PostDiffUtil) {
    class PostViewHolder( private val binding : PostItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(post: Post?){
            binding.itemName.text = post?.postName
            binding.sellPrice.text = "Ksh "+post?.postItemPrice
            binding.sellLocation.text = post?.postSpecificLoc
            Glide.with(binding.root.context)
                .load(post?.postImage)
                .into(binding.itemImage)
        }
    }

    fun removeItem(item: Post){
        val position = currentList.indexOf(item)
        if (position != -1){
            currentList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    object PostDiffUtil : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(PostItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.itemView.setOnClickListener {
            val action = ViewShopFragmentDirections.actionViewShopFragmentToViewPostFragment(post)
            holder.itemView.findNavController().navigate(action)
        }
        holder.bind(post)
    }

}
