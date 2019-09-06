package com.stonetree.imagebucket.main.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stonetree.imagebucket.core.NetworkState
import com.stonetree.imagebucket.main.model.MainModel
import com.stonetree.imagebucket.main.resources.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val images: LiveData<List<MainModel>> = repository.getImages()

    val network: LiveData<NetworkState> = repository.getState()

    fun delete(name: String) {
        repository.delete(name)
    }

    init {
        viewModelScope.launch {
            repository.getAllImages()
        }
    }

    fun uploadImage(uri: Uri) {
        viewModelScope.launch {
            repository.uploadImage(uri)
        }
    }
}