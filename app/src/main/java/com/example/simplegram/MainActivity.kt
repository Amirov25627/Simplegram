package com.example.simplegram

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simplegram.viewmodel.Factory
import com.example.simplegram.viewmodel.ViewModel
import java.io.IOException

lateinit var viewModel: ViewModel
 var uri: Uri? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //lock portrait orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //lock Night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        val factory = Factory(application)
        viewModel = ViewModelProvider(this, factory).get(
            ViewModel::class.java
        )
        viewModel.initializeDbRef()


    }


}

