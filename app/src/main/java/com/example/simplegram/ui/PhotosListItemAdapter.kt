package com.example.simplegram.ui

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simplegram.databinding.PhotosListItemBinding
import com.example.simplegram.network.PostData

class PhotosListItemAdapter() : RecyclerView.Adapter<PhotosViewHolder>() {

    private val mList = mutableListOf<PostData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PhotosListItemBinding.inflate(inflater, parent, false)
        return PhotosViewHolder(
            binding)
    }

    override fun getItemCount(): Int {

        return mList.size
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    fun updatePost(arrayList: ArrayList<PostData>){
        mList.clear()
        mList.addAll(arrayList)
        notifyDataSetChanged()
    }
}

class PhotosViewHolder(private val binding: PhotosListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: PostData) {
        Log.d("DATTT", data.description)
        val uri = Uri.parse(data.photo)
        Glide
            .with(binding.root.context)
            .load(uri)
            .into(binding.photo)

        binding.description.text = data.description


    }
}