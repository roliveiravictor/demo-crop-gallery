package com.stonetree.imagebucket.main.view

import android.content.ComponentName
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.net.Uri
import android.os.Looper.getMainLooper
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stonetree.imagebucket.R
import com.stonetree.imagebucket.core.constants.Constants
import com.stonetree.imagebucket.core.constants.Constants.REQUEST_CODE
import com.stonetree.imagebucket.core.extensions.getCachedImage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_EXTRA_RESULT
import com.theartofdev.edmodo.cropper.CropImage.getActivityResult
import junit.framework.TestCase.*
import kotlinx.android.synthetic.main.activity_gallery.*
import manifest.stonetree.com.br.permissions.constants.Permission.CAMERA
import manifest.stonetree.com.br.permissions.feature.Request
import manifest.stonetree.com.br.permissions.feature.model.Device
import org.junit.Before
import org.junit.Ignore
import org.koin.test.AutoCloseKoinTest
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.Shadows
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
class GalleryActivityTest : AutoCloseKoinTest() {

    private lateinit var activity: GalleryActivity

    @Before
    fun setup() {
        ActivityScenario.launch(GalleryActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                this.activity = activity
            }
        }
    }

    @Test
    fun upload_shouldReturnGalleryIntent() {
        activity.upload.callOnClick()

        val shadowIntent = shadowOf(activity).nextStartedActivity
        val galleryIntent = Intent(ACTION_PICK, EXTERNAL_CONTENT_URI).apply {
            type = Constants.IMAGE_MYME_TYPE
        }

        assertTrue(galleryIntent.filterEquals(shadowIntent))
    }

    @Test
    fun onPermissionGranted_withRightRequest_shouldReturnIntent() {
        activity.onPermissionGranted(REQUEST_CODE)

        val shadowIntent = shadowOf(activity).nextStartedActivity
        val cameraIntent = Intent(ACTION_IMAGE_CAPTURE)

        assertTrue(cameraIntent.filterEquals(shadowIntent))
    }

    @Test
    fun onPermissionGranted_withWrongRequest_shouldDoNothing() {
        activity.onPermissionGranted(-1)

        val shadowIntent = shadowOf(activity).nextStartedActivity

        assertNull(shadowIntent)
    }

    @Test
    fun onPermissionDenied_shouldReturnToast() {
        activity.onPermissionDenied()
        assertEquals("Permissions denied.", ShadowToast.getTextOfLatestToast());
    }

    @Test
    fun openCamera_withPermissionDenied_shouldReturnIntent() {
        activity.camera.callOnClick()

        val shadowIntent = shadowOf(activity).nextStartedActivity

        val componentName = ComponentName(
            "com.stonetree.imagebucket",
            "manifest.stonetree.com.br.permissions.feature.Request"
        )
        val cameraIntent = Intent().setComponent(componentName)

        assertTrue(cameraIntent.filterEquals(shadowIntent))
    }

    @Test
    fun delete_withEmptyName_shouldDoNothing() {
        activity.delete("")
    }

    @Test
    fun delete_withBlankName_shouldDoNothing() {
        activity.delete("   ")
    }

    @Test
    fun delete_withName_shouldDeleteReference() {
        activity.delete("mName")
    }

    @Test
    fun onActivityResult_withData_shouldExecuteGallery() {
        activity.openGallery()
        Intent(Intent.ACTION_PICK, EXTERNAL_CONTENT_URI).also { galleryIntent ->
            galleryIntent.type = Constants.IMAGE_MYME_TYPE
            galleryIntent.data = mock(Uri::class.java)
            shadowOf(activity).apply {
                receiveResult(
                    nextStartedActivity,
                    0,
                    galleryIntent
                )
            }
        }
    }

    @Test
    fun onActivityResult_withoutData_shouldExecuteGallery() {
        activity.openGallery()
        Intent(Intent.ACTION_PICK, EXTERNAL_CONTENT_URI).also { galleryIntent ->
            galleryIntent.type = Constants.IMAGE_MYME_TYPE
            shadowOf(activity).apply {
                receiveResult(
                    nextStartedActivity,
                    0,
                    galleryIntent
                )
            }
        }
    }

    @Test
    @Ignore
    fun onActivityResult_afterCrop_shouldReturnUpload() {
        val uri = Uri.parse("")
        val cropActivity = CropImage.activity(uri)
        cropActivity.start(activity)
        shadowOf(getMainLooper()).idle()

        cropActivity.getIntent(activity.applicationContext).also { cropIntent ->
            cropIntent.putExtra(CROP_IMAGE_EXTRA_RESULT, uri)
            shadowOf(activity).apply {
                receiveResult(
                    nextStartedActivity,
                    0,
                    cropIntent
                )
            }
        }
    }

    @Test
    fun onActivityResult_withoutIntent_shouldDoNothing() {
        activity.onPermissionGranted(REQUEST_CODE)
        shadowOf(activity).apply {
            receiveResult(
                nextStartedActivity,
                0,
                null
            )
        }
    }

    @Test
    fun onActivityResult_shouldExecuteCamera() {
        activity.onPermissionGranted(REQUEST_CODE)
        Intent(ACTION_IMAGE_CAPTURE).also { cameraIntent ->
            cameraIntent.getCachedImage(activity)
            shadowOf(activity).apply {
                receiveResult(
                    nextStartedActivity,
                    0,
                    cameraIntent
                )
            }
        }
    }
}