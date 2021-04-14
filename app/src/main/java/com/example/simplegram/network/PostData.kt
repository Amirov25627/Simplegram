package com.example.simplegram.network

import com.example.simplegram.viewModel
import com.google.firebase.database.DataSnapshot
import java.lang.Exception


class PostData(snapshot: DataSnapshot?) {
    lateinit var photo: String
    lateinit var description: String


    init {
        if (snapshot != null ) {
            createDataFromSnapshot(snapshot)
        }
    }

    private fun createDataFromSnapshot(snapshot: DataSnapshot) {
        try {
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            photo = data["photo"] as String
            description = data["description"] as String
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toMap(): HashMap<String, Any> {
        val map: HashMap<String, Any> = HashMap()
        map["photo"] = photo
        map["description"] = description

        return map
    }


}
