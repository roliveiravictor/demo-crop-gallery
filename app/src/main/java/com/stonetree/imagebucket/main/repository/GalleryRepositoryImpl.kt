package com.stonetree.imagebucket.main.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.stonetree.imagebucket.core.constants.CloudFunctions.GET_IMAGES
import com.stonetree.imagebucket.core.constants.Constants.FIREBASE_IMAGES_PATH
import com.stonetree.imagebucket.core.extensions.downloadReference
import com.stonetree.imagebucket.core.extensions.uploadReference
import com.stonetree.imagebucket.core.network.NetworkState
import com.stonetree.imagebucket.main.model.GalleryModel
import com.stonetree.imagebucket.main.model.Meta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

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
        val firebasePath =
            this@GalleryRepositoryImpl.storageReference.child(
                FIREBASE_IMAGES_PATH + UUID.randomUUID().toString()
            )

        network.postValue(NetworkState.LOADING)
        firebasePath.putFile(uri).addOnSuccessListener { uploadedImage ->
            uploadedImage.storage.downloadUrl.addOnCompleteListener { urlDownload ->
                urlDownload.result?.let { uri ->
                    val newImages =
                        mutableListOf(GalleryModel(uri, uri.toString().uploadReference()))
                    images.value?.let { oldImages ->
                        newImages.addAll(oldImages)
                    }
                    images.postValue(newImages)
                    network.postValue(NetworkState.LOADED)
                }
            }
        }
    }

    override fun getAllImages() {
        val cloudFunction = functions.getHttpsCallable(GET_IMAGES)

        network.postValue(NetworkState.LOADING)
        cloudFunction.call().addOnSuccessListener { response ->
            val response = Gson().fromJson(response.data.toString(), Meta::class.java)
            val models = arrayListOf<GalleryModel>()
            response.urls.forEach { reference ->
                reference.mediaLink.apply {
                    models.add(GalleryModel(Uri.parse(this), this.downloadReference()))
                }
            }
            images.postValue(models)
            network.postValue(NetworkState.LOADED)
        }.addOnFailureListener { error ->
            Log.w(javaClass.name, error.message)
        }
    }

    override fun delete(name: String) {
        network.postValue(NetworkState.LOADING)
        storageReference.child(name).delete().addOnSuccessListener {
            CoroutineScope(Dispatchers.IO).launch {
                getAllImages()
            }
        }.addOnFailureListener { error ->
            Log.w(javaClass.name, error.message)
        }
    }
}
