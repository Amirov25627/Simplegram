package com.example.simplegram.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.simplegram.viewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

object PostModel : Observable() {
    private var mValueDataListener: ValueEventListener? = null
    private var mPostList: MutableLiveData<ArrayList<PostData>>? = MutableLiveData()

     fun getDatabaseRef(): DatabaseReference? {
        return FirebaseDatabase.getInstance().reference.child("post")
    }

    init {
        if (mValueDataListener != null) {
            getDatabaseRef()?.removeEventListener(mValueDataListener!!)
        }
        mValueDataListener = null
        Log.i("PostModel", "data init line 22")

        mValueDataListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                if (error != null) {
                    Log.i("postmodel", "data update cancelled line 27" + error.message)
                }
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    Log.i("postmodel", "data updated line 30")
                    val data: ArrayList<PostData> = ArrayList()
                    if (dataSnapshot != null) {
                        for (snapshot: DataSnapshot in dataSnapshot.children) {
                            try {
                                data.add(PostData(snapshot))
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        mPostList?.postValue(data)
                        Log.i(
                            "postmodel",
                            "data updated, " + mPostList!!.value?.size + mPostList

                        )
                        setChanged()
                        notifyObservers()
                        viewModel.splash.value = true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }



    fun savePost(c: PostData, onCompleteListener: OnCompleteListener<Void>?) {
        val reference = getDatabaseRef()?.push()
        reference?.updateChildren(c.toMap())?.addOnCompleteListener { task ->
            if (task.isComplete) {
                onCompleteListener?.onComplete(task)
                setChanged()
                notifyObservers()
            }
        }
    }
    fun getPostsLiveData()= mPostList
}