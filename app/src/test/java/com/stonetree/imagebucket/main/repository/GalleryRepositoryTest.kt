package com.stonetree.imagebucket.main.repository

import android.net.Uri
import com.google.firebase.storage.UploadTask
import com.stonetree.imagebucket.core.constants.Constants
import com.stonetree.imagebucket.core.constants.Constants.FIREBASE_IMAGES_PATH
import com.stonetree.imagebucket.core.extensions.referenceHash
import com.stonetree.imagebucket.core.network.NetworkState.Companion.LOADING
import com.stonetree.imagebucket.gallery.repository.GalleryRepositoryImpl
import com.stonetree.imagebucket.mocker.FirebaseRepositoryMockerImpl
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

class GalleryRepositoryTest : FirebaseRepositoryMockerImpl() {

    private val storage = mockFirebaseStorage()

    private val functions = mockFirebaseFunctions()

    private lateinit var repository: GalleryRepositoryImpl

    @Before
    fun setup() {
        repository = GalleryRepositoryImpl(storage, functions)
    }

    @Test
    @Ignore
    fun uploadImage_withEmptyUri_shouldDoNothing() {
        FIREBASE_IMAGES_PATH.apply {
            `when`(referenceHash()).thenReturn(this)
            mockPutFile(storage, this)
        }

        repository.uploadImage(mock(Uri::class.java))
        assertEquals(LOADING, repository.getState())
    }
}