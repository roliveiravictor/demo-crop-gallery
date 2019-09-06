package com.stonetree.imagebucket.main.repository

import android.R
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stonetree.imagebucket.core.Constants
import com.stonetree.imagebucket.core.getCachedImage
import com.stonetree.imagebucket.core.launchIdling
import com.stonetree.imagebucket.main.model.MainModel
import com.stonetree.imagebucket.main.resources.idle.EspressoIdlingResource
import com.stonetree.imagebucket.main.resources.repository.MainRepositoryImpl
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class MainRepositoryImplTest {

    private var repositoryImpl: MainRepositoryImpl? = null

    private var idling = EspressoIdlingResource.getIdlingResource()

    @Before
    fun setup() {
        repositoryImpl = MainRepositoryImpl()
    }

    @After
    fun unregisterIdlingResource() {
        if (idling != null) {
            IdlingRegistry.getInstance().unregister(idling)
        }
    }

    @Test
    fun test_singleton_shouldReturnNotNull() {
        assertNotNull(MainRepositoryImpl())
    }

    fun test_upload_shouldReturnLiveDataUpdate() {
        val liveData = MutableLiveData<List<MainModel>>()
        val image = createImageFile()

        assertNull(liveData.value)

        runBlocking {
            launchIdling {
                repositoryImpl?.uploadImage(Uri.fromFile(image))
                val uploadedImage = liveData.value?.first()
                assertNotNull(uploadedImage)
            }
        }
    }

    private fun createImageFile(): File {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.arrow_down_float)

        val intent = Intent()
        intent.putExtra(Constants.INTENT_EXTRA_DATA, bitmap)

        return intent.getCachedImage(context)
    }
}