package com.stonetree.imagebucket.main.repository

import org.junit.Test
import org.mockito.Mockito.mock

class GalleryRepositoryTest {

    private val repository = mock(GalleryRepositoryImpl::class.java)

    @Test
    fun uploadImage_withEmptyUri_shouldDoNothing() {
    }
}