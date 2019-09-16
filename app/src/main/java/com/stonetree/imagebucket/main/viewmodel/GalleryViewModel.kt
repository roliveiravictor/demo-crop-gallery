package com.stonetree.imagebucket.main.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stonetree.imagebucket.core.network.NetworkState
import com.stonetree.imagebucket.main.model.GalleryModel
import com.stonetree.imagebucket.main.repository.GalleryRepository
import kotlinx.coroutines.launch

class GalleryViewModel(private val repository: GalleryRepository) : ViewModel() {

    val images: LiveData<List<GalleryModel>> = repository.getImages()

    val network: LiveData<NetworkState> = repository.getState()

    init {
        repository.getAllImages()
    }

    fun uploadImage(uri: Uri) {
        repository.uploadImage(uri)
    }

    fun delete(name: String) {
        repository.delete(name)
    }
}
