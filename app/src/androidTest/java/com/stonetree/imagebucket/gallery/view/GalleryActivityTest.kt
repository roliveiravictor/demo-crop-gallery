package com.stonetree.imagebucket.gallery.view

import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.airbnb.lottie.LottieAnimationView
import com.stonetree.imagebucket.R
import com.theartofdev.edmodo.cropper.CropImage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class MainViewTest {

    @Rule
    @JvmField
    val view = IntentsTestRule(GalleryActivity::class.java)

    private val dummy = Instrumentation.ActivityResult(
        -1,
        Intent()
    )

    @Before
    fun setup() {
        view.activity.apply {
            runOnUiThread {
                findViewById<LottieAnimationView>(R.id.loading).visibility = View.GONE
            }
        }
    }

    @Test
    fun upload_shouldReturnOpenGallery() {
        toPackage("com.google.android.apps.photos").apply {
            intending(this).respondWith(dummy)
            onView(withId(R.id.upload)).perform(click())
            intended(this)
        }
    }

    @Test
    fun camera_shouldReturnRequestPermission() {
        mock(CropImage.ActivityResult::class.java).apply {
            `when`(uri).thenReturn(mock(Uri::class.java))
        }

        toPackage("com.android.camera").apply {
            intending(this).respondWith(dummy)
            onView(withId(R.id.camera)).perform(click())
            intended(this)
        }
    }
}