package com.stonetree.imagebucket

import android.app.Application
import com.google.firebase.FirebaseApp
import com.stonetree.imagebucket.core.injector.Injector
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class ImageBucketApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this);

        startKoin {
            androidLogger()
            androidContext(this@ImageBucketApplication)
            Injector().apply {
                loadKoinModules(generateAppModules())
            }
        }
    }
}
