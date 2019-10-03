package com.stonetree.imagebucket.gallery.repository

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
import com.stonetree.imagebucket.gallery.model.GalleryModel
import com.stonetree.imagebucket.gallery.model.Meta

class GalleryRepositoryImpl(
    private val storage: FirebaseStorage,
    private val functions: FirebaseFunctions
) : GalleryRepository {

    private val images = MutableLiveData<List<GalleryModel>>()

    private val network = MutableLiveData<NetworkState>()

    override fun getImages(): MutableLiveData<List<GalleryModel>> {
        return images
    }

    override fun getState(): MutableLiveData<NetworkState> {
        return network
    }

    override fun uploadImage(uri: Uri, hash: String) {
        network.postValue(NetworkState.LOADING)
        storage.reference.child(hash)
            .putFile(uri).addOnSuccessListener { task ->
                requestDownloadUrl(task)
            }
    }

    private fun requestDownloadUrl(task: UploadTask.TaskSnapshot) {
        task.storage.downloadUrl.addOnCompleteListener { reference ->
            updateImagesReference(reference)
        }
    }

    private fun updateImagesReference(reference: Task<Uri>) {
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
                Gson().fromJson(
                    response.data.toString(),
                    Meta::class.java
                ).apply {
                    parseImageReferences(this).let { stored ->
                        images.postValue(stored)
                    }
                    network.postValue(NetworkState.LOADED)
                }
            }
    }

    private fun parseImageReferences(response: Meta): List<GalleryModel> {
        return arrayListOf<GalleryModel>().also { models ->
            response.urls.forEach { reference ->
                reference.mediaLink.apply {
                    models.add(
                        GalleryModel(
                            Uri.parse(this),
                            downloadReference()
                        )
                    )
                }
            }
        }
    }


    override fun delete(id: String) {
        if (id.isNotBlank()) {
            storage.reference.child(id)
                .delete()
                .addOnSuccessListener { getAllImages() }
        }
    }
}
