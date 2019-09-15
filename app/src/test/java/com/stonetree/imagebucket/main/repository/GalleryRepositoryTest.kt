package com.stonetree.imagebucket.main.repository

import android.net.Uri
import org.junit.Test
import org.mockito.Mockito.*

class GalleryRepositoryTest {

    private val repository = mock(GalleryRepositoryImpl::class.java)

    @Test
    fun uploadImage_withEmptyUri_shouldDoNothing() {
        verify(repository).uploadImage(Uri.parse("mUri"))
    }
}