package com.example.simplegram.viewmodel


import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simplegram.network.PostData
import com.example.simplegram.network.PostModel
import com.example.simplegram.network.PostModel.getDatabaseRef
import com.example.simplegram.viewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.lang.Exception


open class ViewModel(app: Application) : ViewModel() {
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private var downloadUri: String? = null
    var finish = MutableLiveData<Boolean>()
    var splash = MutableLiveData<Boolean>()


    fun initializeDbRef() {
        database = Firebase.database.reference
        storage = Firebase.storage
    }


    fun savePost(photo: Uri?, description: String) {
        //download photo to storage and get url string
        val storageRef = storage.reference
        val photosRef = storageRef.child("${photo?.lastPathSegment?.takeLast(3)}.jpg")
        val uploadTask = photosRef.putFile(photo!!)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            photosRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                downloadUri = task.result.toString()


                val post = PostData(null)
                post.photo = downloadUri.toString()
                post.description = description

                PostModel.savePost(post, OnCompleteListener { task ->
                    if (task.isComplete) {
                        finish.value = true
                    }
                })

            }
        }
    }
}







