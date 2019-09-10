package com.stonetree.imagebucket.core.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import com.stonetree.imagebucket.core.constants.Constants
import java.io.File
import java.io.FileOutputStream

fun Intent.getCachedImage(context: Context): File {
    val image = createTemporaryFile(context)
    val output = FileOutputStream(image)
    (this?.extras?.get(Constants.INTENT_EXTRA_DATA) as Bitmap).let { bitmap ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, Constants.BITMAP_QUALITY, output)
        output.flush()
        output.close()
    }
    return image
}

fun createTemporaryFile(context: Context): File {
    return File.createTempFile(
        "img_",
        ".jpg",
        context.cacheDir
    )
}