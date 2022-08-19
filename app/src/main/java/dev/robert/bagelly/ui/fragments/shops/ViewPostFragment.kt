package dev.robert.bagelly.ui.fragments.shops

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.adapter.ShopPostAdapter
import dev.robert.bagelly.databinding.FragmentViewPostBinding
import dev.robert.bagelly.model.Post
import dev.robert.bagelly.ui.fragments.shops.viewmodel.ShopsViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewPostFragment : Fragment() {
    private lateinit var binding: FragmentViewPostBinding
    private val args by navArgs<ViewPostFragmentArgs>()
    private val viewModel: ShopsViewModel by viewModels()
    private val adapter by lazy { ShopPostAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewPostBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.postsToolbar)
        (activity as AppCompatActivity).supportActionBar?.title = args.postDetails.postName
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadDetails()


        return view
    }

    private fun loadDetails() {
        Glide.with(this)
            .load(args.postDetails.postImage)
            .into(binding.postImage)

        binding.tvPostTitle.text = args.postDetails.postName
        binding.tvPostDescription.text = args.postDetails.postDescription
        binding.tvPostPrice.text = args.postDetails.postItemPrice
        binding.tvPostLocation.text = args.postDetails.postSpecificLoc
        binding.tvPostCondition.text = args.postDetails.postCondition
        //binding.tvPostType.text = args.postDetails.postType
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteAction -> {
                val alertDialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                alertDialog.setTitle("Delete Post")
                alertDialog.setMessage("Are you sure you want to delete this post?")
                alertDialog.setPositiveButton("Yes"){dialog, which ->
                    //delete post
                    deletePost()
                    dialog.dismiss()
                    requireActivity().onBackPressed()
                }
                alertDialog.setNegativeButton("No"){dialog, which ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deletePost() {
        val post = Post(
            args.postDetails.postId,
            args.postDetails.ownerId,
            args.postDetails.postImage,
            args.postDetails.postName,
            args.postDetails.postType,
            args.postDetails.postCondition,
            args.postDetails.postDescription,
            args.postDetails.postSpecificLoc,
            args.postDetails.postItemPrice
        )
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.deletePost(
              post
            )
        }
        adapter.removeItem(post)
    }
}