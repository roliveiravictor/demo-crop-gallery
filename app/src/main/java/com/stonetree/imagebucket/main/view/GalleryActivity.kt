package com.stonetree.imagebucket.main.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.stonetree.imagebucket.R
import com.stonetree.imagebucket.core.constants.Constants.IMAGE_MYME_TYPE
import com.stonetree.imagebucket.core.constants.Constants.REQUEST_CODE
import com.stonetree.imagebucket.core.extensions.getCachedImage
import com.stonetree.imagebucket.databinding.ActivityGalleryBinding
import com.stonetree.imagebucket.main.view.adapter.GalleryAdapter
import com.stonetree.imagebucket.main.viewmodel.GalleryViewModel
import com.theartofdev.edmodo.cropper.CropImage.activity
import com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
import com.theartofdev.edmodo.cropper.CropImage.getActivityResult
import manifest.stonetree.com.br.permissions.constants.Permission.CAMERA
import manifest.stonetree.com.br.permissions.constants.Permission.READ_EXTERNAL_STORAGE
import manifest.stonetree.com.br.permissions.constants.Permission.WRITE_EXTERNAL_STORAGE
import manifest.stonetree.com.br.permissions.feature.IManifestCallback
import manifest.stonetree.com.br.permissions.feature.Manifest
import manifest.stonetree.com.br.permissions.feature.model.Device
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryActivity : AppCompatActivity(), IManifestCallback, IPicture {

    private val vm: GalleryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data: ActivityGalleryBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_gallery
        )
        val adapter = GalleryAdapter(this)

        bindXml(data, adapter)
        bindObservers(data, adapter)
    }

    private fun bindObservers(
        data: ActivityGalleryBinding,
        adapter: GalleryAdapter
    ) {
        vm.images.observe(this@GalleryActivity) { images ->
            adapter.submitList(images)
        }

        vm.network.observe(this@GalleryActivity) { state ->
            data.network = state
        }
    }

    private fun bindXml(
        data: ActivityGalleryBinding,
        adapter: GalleryAdapter
    ) {
        data.view = this@GalleryActivity
        data.gallery.adapter = adapter
    }

    override fun onActivityResult(request: Int, result: Int, intent: Intent?) {
        super.onActivityResult(request, result, intent)
        intent?.apply {
            when (request) {
                CAMERA.value -> callCameraEditor(this)
                READ_EXTERNAL_STORAGE.value -> callGalleryEditor(this)
                CROP_IMAGE_ACTIVITY_REQUEST_CODE -> vm.uploadImage(getActivityResult(this).uri)
            }
        }
    }

    private fun callCameraEditor(intent: Intent) {
        val image = intent.getCachedImage(this)
        val uri = Uri.fromFile(image)
        activity(uri).start(this)
    }

    private fun callGalleryEditor(intent: Intent) {
        intent?.data?.let { uri ->
            activity(uri).start(this@GalleryActivity)
        }
    }

    fun requestCamera(view: View) {
        val permissions = arrayOf(
            CAMERA.key,
            READ_EXTERNAL_STORAGE.key,
            WRITE_EXTERNAL_STORAGE.key
        )

        val device = Device.Builder()
            .setPermission(permissions)
            .setRequestCode(REQUEST_CODE)
            .setCallback(this)
            .build()

        Manifest.request(device, this)
    }

    fun requestGallery(view: View) {
        val galleryIntent = Intent(Intent.ACTION_PICK, EXTERNAL_CONTENT_URI)
        galleryIntent.type = IMAGE_MYME_TYPE
        startActivityForResult(galleryIntent, READ_EXTERNAL_STORAGE.value)
    }

    private fun openCamera() {
        val cameraIntent = Intent(ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA.value)
    }

    override fun onPermissionGranted(requestCode: Int) {
        if (requestCode == REQUEST_CODE)
            openCamera()
    }

    override fun onPermissionDenied() {
        Toast.makeText(
            this,
            getString(R.string.permission_denied),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun delete(name: String) {
        vm.delete(name)
    }
}
