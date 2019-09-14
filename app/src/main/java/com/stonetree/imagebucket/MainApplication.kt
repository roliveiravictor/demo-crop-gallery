package com.stonetree.imagebucket

import android.app.Application
import com.stonetree.imagebucket.main.repository.MainRepository
import com.stonetree.imagebucket.main.repository.MainRepositoryImpl
import com.stonetree.imagebucket.main.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(createModule())
        }
    }

    private fun createModule() = module {
        single<MainRepository> { MainRepositoryImpl() }
        viewModel { MainViewModel(get()) }
    }
}
