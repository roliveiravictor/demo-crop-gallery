package com.stonetree.imagebucket.core.injector

import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.stonetree.imagebucket.gallery.repository.GalleryRepository
import com.stonetree.imagebucket.gallery.repository.GalleryRepositoryImpl
import com.stonetree.imagebucket.gallery.view.Picture
import com.stonetree.imagebucket.gallery.view.adapter.GalleryAdapter
import com.stonetree.imagebucket.gallery.viewmodel.GalleryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class Injector {

    private val gallery = module {
        factory { (picture: Picture) -> GalleryAdapter(picture) }

        factory<GalleryRepository> {
            GalleryRepositoryImpl(
                FirebaseStorage.getInstance(),
                FirebaseFunctions.getInstance()
            )
        }
        viewModel { GalleryViewModel(get()) }
    }

    fun startModules(): List<Module> {
        return arrayListOf(gallery)
    }
}