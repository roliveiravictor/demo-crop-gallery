package com.stonetree.imagebucket.main.repository

import com.stonetree.imagebucket.gallery.repository.GalleryRepositoryImpl
import com.stonetree.imagebucket.gallery.viewmodel.GalleryViewModel
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mockito.verify

class GalleryRepositoryTest : AutoCloseKoinTest() {

    private lateinit var repository: GalleryRepositoryImpl

    @Before
    fun setup() {
        repository = GalleryRepositoryImpl()
    }

    @Test
    @Ignore
    fun uploadImage_withEmptyUri_shouldDoNothing() {

    }
}