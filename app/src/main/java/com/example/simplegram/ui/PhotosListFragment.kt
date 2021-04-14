package com.example.simplegram.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.simplegram.R
import com.example.simplegram.databinding.PhotosListFragmentBinding
import com.example.simplegram.viewmodel.ViewModel


class PhotosListFragment : Fragment() {
    private lateinit var binding: PhotosListFragmentBinding
    private val viewModel: ViewModel by activityViewModels()
    lateinit var adapter: PhotosListItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PhotosListFragmentBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PhotosListItemAdapter(viewModel)
        binding.photosRV.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            adapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.addPost.setOnClickListener {
            viewModel.finish.value = false
            findNavController().navigate(R.id.action_photosListFragment_to_uploadingFragment)
        }


    }


}




