package com.stonetree.imagebucket.main.repository

import android.net.Uri
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.stonetree.imagebucket.core.constants.Constants.FIREBASE_IMAGES_PATH
import com.stonetree.imagebucket.core.network.NetworkState.Companion.LOADING
import com.stonetree.imagebucket.gallery.repository.GalleryRepositoryImpl
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GalleryRepositoryTest {

    /**
     * Warning:
     * https://static.javadoc.io/org.mockito/
     * mockito-core/2.7.14/org/mockito/Mockito.html#RETURNS_DEEP_STUBS
     */

    private val storage = mock(FirebaseStorage::class.java, RETURNS_DEEP_STUBS)

    private val functions = mock(FirebaseFunctions::class.java, RETURNS_DEEP_STUBS)

    private lateinit var repository: GalleryRepositoryImpl

    @Before
    fun setup() {
        repository = GalleryRepositoryImpl(storage, functions)
    }

    @Test
    fun uploadImage_withEmptyUri_shouldDoNothing() {
        val hash = FIREBASE_IMAGES_PATH
        val uri = Uri.parse(hash)

        `when`(
            storage.
                reference
                .child(hash)
                .putFile(uri)
        ).thenReturn(mock(UploadTask::class.java))

        repository.uploadImage(uri, hash)

        assertEquals(LOADING, repository.getState().value)
    }
}