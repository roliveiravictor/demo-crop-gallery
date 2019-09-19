package com.stonetree.imagebucket.main.view

import android.content.ComponentName
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ActivityScenario
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stonetree.imagebucket.R
import com.stonetree.imagebucket.core.constants.Constants
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import manifest.stonetree.com.br.permissions.constants.Permission.CAMERA
import org.junit.Ignore
import org.koin.test.AutoCloseKoinTest
import org.robolectric.Shadows

@RunWith(RobolectricTestRunner::class)
class GalleryActivityTest : AutoCloseKoinTest() {

    private val CAMERA_PERMISSION = "android.permission.CAMERA"

    @Test
    fun openGallery_shouldReturnIntent() {
        ActivityScenario.launch(GalleryActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                activity.findViewById<FloatingActionButton>(R.id.upload).callOnClick()

                val shadowIntent = Shadows.shadowOf(activity).nextStartedActivity
                val galleryIntent = Intent(ACTION_PICK, EXTERNAL_CONTENT_URI).apply {
                    type = Constants.IMAGE_MYME_TYPE
                }

                assertTrue(galleryIntent.filterEquals(shadowIntent))
            }
        }
    }

    @Test
    @Ignore
    fun openCamera_withPermissionGranted_shouldReturnIntent() {
        ActivityScenario.launch(GalleryActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                activity.onPermissionGranted(CAMERA.value)

                val shadow = Shadows.shadowOf(activity)
                shadow.grantPermissions(CAMERA_PERMISSION)

                val cameraIntent = Intent(ACTION_IMAGE_CAPTURE)

                assertTrue(cameraIntent.filterEquals(shadow.nextStartedActivityForResult.intent))
            }
        }
    }

    @Test
    fun openCamera_withPermissionDenied_shouldReturnIntent() {
        ActivityScenario.launch(GalleryActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                activity.findViewById<FloatingActionButton>(R.id.camera).callOnClick()

                val shadowIntent = Shadows.shadowOf(activity).nextStartedActivity

                val componentName = ComponentName(
                    "com.stonetree.imagebucket",
                    "manifest.stonetree.com.br.permissions.feature.Request"
                )
                val cameraIntent = Intent().setComponent(componentName)

                assertTrue(cameraIntent.filterEquals(shadowIntent))
            }
        }
    }
}