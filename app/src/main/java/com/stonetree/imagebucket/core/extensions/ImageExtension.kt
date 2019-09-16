package com.stonetree.imagebucket.core.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.JPEG
import com.stonetree.imagebucket.core.constants.Constants.BITMAP_QUALITY
import com.stonetree.imagebucket.core.constants.Constants.INTENT_EXTRA_DATA
import java.io.File
import java.io.FileOutputStream

fun Intent.getCachedImage(context: Context): File {
    val image = File.createTempFile(
        "img_",
        ".jpg",
        context.cacheDir
    )
    FileOutputStream(image).apply {
        (extras?.get(INTENT_EXTRA_DATA) as? Bitmap).let { bitmap ->
            bitmap?.compress(JPEG, BITMAP_QUALITY, this)
            flush()
            close()
        }
    }

    return image
}
