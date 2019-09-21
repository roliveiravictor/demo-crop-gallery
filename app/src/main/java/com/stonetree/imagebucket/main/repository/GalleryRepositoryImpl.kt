package com.stonetree.imagebucket.main.repository

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.gson.Gson
import com.stonetree.imagebucket.core.constants.CloudFunctions.GET_IMAGES
import com.stonetree.imagebucket.core.constants.Constants.FIREBASE_IMAGES_PATH
import com.stonetree.imagebucket.core.extensions.downloadReference
import com.stonetree.imagebucket.core.extensions.referenceHash
import com.stonetree.imagebucket.core.extensions.uploadReference
import com.stonetree.imagebucket.core.network.NetworkState
import com.stonetree.imagebucket.main.model.GalleryModel
import com.stonetree.imagebucket.main.model.Meta

class GalleryRepositoryImpl : GalleryRepository {
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

    private val functions = FirebaseFunctions.getInstance()

    private val images = MutableLiveData<List<GalleryModel>>()

    private val network = MutableLiveData<NetworkState>()

    override fun getImages(): MutableLiveData<List<GalleryModel>> {
        return images
    }

    override fun getState(): MutableLiveData<NetworkState> {
        return network
    }

    override fun uploadImage(uri: Uri) {
        network.postValue(NetworkState.LOADING)
        storageReference.child(
            FIREBASE_IMAGES_PATH.referenceHash()
        ).putFile(uri).addOnSuccessListener { task ->
            executeUpload(task)
        }
    }

    private fun executeUpload(task: UploadTask.TaskSnapshot) {
        task.storage.downloadUrl.addOnCompleteListener { reference ->
            updateImageReferences(reference)
        }
    }

    private fun updateImageReferences(reference: Task<Uri>) {
        reference.result?.let { uploadedImage ->
            val newImage = GalleryModel(
                uploadedImage,
                uploadedImage.toString().uploadReference()
            )

            mutableListOf(newImage).apply {
                images.value?.let { oldImages ->
                    addAll(oldImages)
                }
                images.postValue(this)
            }
            network.postValue(NetworkState.LOADED)
        }
    }

    override fun getAllImages() {
        network.postValue(NetworkState.LOADING)

        functions.getHttpsCallable(GET_IMAGES)
            .call()
            .addOnSuccessListener { response ->
                val response = Gson().fromJson(response.data.toString(), Meta::class.java)

                parseImageReferences(response).let { stored ->
                    images.postValue(stored)
                }

                network.postValue(NetworkState.LOADED)
            }
    }

    private fun parseImageReferences(response: Meta): List<GalleryModel> {
        val models = arrayListOf<GalleryModel>()
        response.urls.forEach { reference ->
            reference.mediaLink.apply {
                models.add(GalleryModel(Uri.parse(this), this.downloadReference()))
            }
        }
        return models
    }


    override fun delete(name: String) {
        if(name.isNotBlank()) {
            storageReference.child(name)
                .delete()
                .addOnSuccessListener { getAllImages() }
        }
    }
}
