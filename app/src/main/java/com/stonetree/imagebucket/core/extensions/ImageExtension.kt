package com.stonetree.imagebucket.core.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import com.stonetree.imagebucket.core.constants.Constants
import com.stonetree.imagebucket.core.constants.Constants.INTENT_EXTRA_DATA
import java.io.File
import java.io.FileOutputStream

fun Intent.getCachedImage(context: Context): File {
    val image = File.createTempFile(
        "img_",
        ".jpg",
        context.cacheDir
    )
    val output = FileOutputStream(image)

    (extras?.get(INTENT_EXTRA_DATA) as? Bitmap).let { bitmap ->
        bitmap?.compress(Bitmap.CompressFormat.JPEG, Constants.BITMAP_QUALITY, output)
        output.flush()
        output.close()
    }

    return image
}
