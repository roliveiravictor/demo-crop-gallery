package com.stonetree.imagebucket.main.repository

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.stonetree.imagebucket.core.network.NetworkState
import com.stonetree.imagebucket.main.model.MainModel

interface MainRepository {

    fun getState(): MutableLiveData<NetworkState>

    fun getImages(): MutableLiveData<List<MainModel>>

    suspend fun uploadImage(uri: Uri)

    suspend fun getAllImages()

    fun delete(id: String)
}
