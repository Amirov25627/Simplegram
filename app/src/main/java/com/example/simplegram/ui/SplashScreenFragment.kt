package com.example.simplegram.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.simplegram.R
import com.example.simplegram.databinding.SplashScreenFragmentBinding
import com.example.simplegram.network.PostData
import com.example.simplegram.network.PostModel
import com.example.simplegram.viewmodel.ViewModel

class SplashScreenFragment : Fragment() {
    private lateinit var binding: SplashScreenFragmentBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SplashScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       PostModel.getDatabaseRef()

       viewModel.splash.observe(viewLifecycleOwner, Observer {
           if(it){
               findNavController().navigate(R.id.action_splashScreenFragment_to_photosListFragment)
           }
       })




    }
}