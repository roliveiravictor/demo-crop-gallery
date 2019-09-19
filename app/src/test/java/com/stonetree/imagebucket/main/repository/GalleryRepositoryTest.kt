package com.stonetree.imagebucket.main.repository

import com.stonetree.imagebucket.main.viewmodel.GalleryViewModel
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

class GalleryRepositoryTest: AutoCloseKoinTest() {

    private val module = module {
        named("repository").apply {
            single<GalleryRepository>(this) { GalleryRepositoryImpl() }
            single { GalleryViewModel(get(this.javaClass)) }
        }
    }

    private val repository: GalleryRepositoryImpl by inject()
    private val vm: GalleryViewModel by inject()

    @Before
    fun setup() {
        startKoin { loadKoinModules(module) }
        declareMock<GalleryRepositoryImpl>()
    }

    @Test
    @Ignore
    fun uploadImage_withEmptyUri_shouldDoNothing() {
        vm.delete("")
        verify(repository).delete("")
    }
}