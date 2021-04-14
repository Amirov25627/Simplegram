package com.example.simplegram.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.simplegram.R

import com.example.simplegram.databinding.UploadingFragmentBinding

import com.example.simplegram.uri
import com.example.simplegram.viewmodel.ViewModel
import java.io.IOException

class UploadingFragment : Fragment() {
    private lateinit var binding: UploadingFragmentBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UploadingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.splash.value = false

        binding.addPhotoButton.setOnClickListener {
            getPhoto()
        }

        binding.inactiveButton.setOnClickListener {
            Toast.makeText(context, "Please, add photo", Toast.LENGTH_SHORT).show()
        }

        binding.publicButton.setOnClickListener {
            viewModel.savePost(uri, binding.newDescription.text.toString())
            Toast.makeText(context, "In process..." , Toast.LENGTH_SHORT).show()
        }

        viewModel.finish.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(R.id.action_uploadingFragment_to_photosListFragment)
            }
        })


    }


    //uploading images
    private fun getPhoto() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "select image"), 1002)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1002) {
            try {
                uri = data?.data
                val bm: Bitmap =
                    MediaStore.Images.Media.getBitmap(activity?.contentResolver, data?.data)
                binding.uploadedPhoto.setImageBitmap(bm)
                binding.uploadedPhoto.visibility = View.VISIBLE
                binding.addPhotoButton.visibility = View.GONE
                binding.inactiveButton.visibility = View.GONE
                binding.publicButton.visibility = View.VISIBLE

            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show()
            }
        }
    }


}