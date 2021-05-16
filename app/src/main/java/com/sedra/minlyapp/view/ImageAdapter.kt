package com.sedra.minlyapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sedra.minlyapp.data.model.ImageItem
import com.sedra.minlyapp.data.remote.ApiService
import com.sedra.minlyapp.databinding.ListItemImageBinding

class CustomViewHolder(val binding: ListItemImageBinding) : RecyclerView.ViewHolder(binding.root)
class ImageAdapter() : ListAdapter<ImageItem, CustomViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<ImageItem>() {
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem._id === newItem._id
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemImageBinding.inflate(inflater, parent, false)

        return CustomViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        Glide.with(holder.binding.root)
            .load(ApiService.BASE_URL + getItem(position).link)
            .into(holder.binding.imageView)
    }

}