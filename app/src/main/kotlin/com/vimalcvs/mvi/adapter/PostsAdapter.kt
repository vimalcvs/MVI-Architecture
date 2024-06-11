package com.vimalcvs.mvi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vimalcvs.mvi.databinding.ItemPostBinding
import com.vimalcvs.mvi.model.ModelPost

@SuppressLint("NotifyDataSetChanged")
class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    private var modelPosts = listOf<ModelPost>()

    private var mOnItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(mItemClickListener: OnItemClickListener?) {
        this.mOnItemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = modelPosts[position]

        holder.binding.title.text = post.title
        holder.binding.body.text = post.body

        holder.binding.itemPost.setOnClickListener {
            mOnItemClickListener?.onItemClick(post)
        }
    }

    override fun getItemCount() = modelPosts.size

    interface OnItemClickListener {
        fun onItemClick(posts: ModelPost)
    }

    fun submitList(modelPosts: List<ModelPost>) {
        this.modelPosts = modelPosts
        notifyDataSetChanged()
    }


    class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}
