package com.aregyan.github.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aregyan.github.databinding.ImageListItemBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class ImageListAdapter @Inject constructor(
    val clickListener: ClickListener
): ListAdapter<ImageViewData, ImageListAdapter.ViewHolder>(ImageDataDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ImageListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ImageListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageViewData, listener: ClickListener) {
            Glide.with(itemView)
                .load(item.imageUrl)
                .centerCrop()
                .into(binding.imageThumbnailIv)
            binding.usernameTv.text = item.username
            itemView.setOnClickListener {
                listener.onClick(item)
            }
        }
    }
}

class ImageDataDiffCallback : DiffUtil.ItemCallback<ImageViewData>() {

    override fun areItemsTheSame(oldItem: ImageViewData, newItem: ImageViewData): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }

    override fun areContentsTheSame(oldItem: ImageViewData, newItem: ImageViewData): Boolean {
        return oldItem == newItem
    }
}

class ClickListener @Inject constructor() {

    var onItemClick: ((ImageViewData) -> Unit)? = null

    fun onClick(data: ImageViewData) {
        onItemClick?.invoke(data)
    }
}