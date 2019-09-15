package com.stonetree.imagebucket.core.injector

import com.stonetree.imagebucket.main.repository.MainRepository
import com.stonetree.imagebucket.main.repository.MainRepositoryImpl
import com.stonetree.imagebucket.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class Injector {

    private val main = module {
        single<MainRepository> { MainRepositoryImpl() }
        viewModel { MainViewModel(get()) }
    }

    fun generateAppModules() : List<Module> {
        return arrayListOf(main)
    }
}