package com.stonetree.imagebucket.core

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.VisibleForTesting
import androidx.databinding.BindingAdapter
import com.stonetree.imagebucket.core.Constants.BITMAP_QUALITY
import com.stonetree.imagebucket.core.Constants.INTENT_EXTRA_DATA
import com.stonetree.imagebucket.core.NetworkState.Companion.LOADING
import com.stonetree.imagebucket.core.Status.FAILED
import com.stonetree.imagebucket.core.Status.SUCCESS
import com.stonetree.imagebucket.main.resources.idle.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@BindingAdapter("isGone")
fun bindIsGone(view: View, network: NetworkState?) {
    when (network) {
        LOADING -> view.visibility = VISIBLE
        else -> view.visibility = GONE
    }
}

@BindingAdapter("isIdle")
fun bindIsIdle(view: View, network: NetworkState?) {
    when (network?.status) {
        SUCCESS -> view.visibility = VISIBLE
        FAILED -> view.visibility = VISIBLE
        else -> view.visibility = GONE
    }
}

fun CoroutineScope.launchIdling(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    EspressoIdlingResource.increment()
    val job = this.launch(context, start, block)
    job.invokeOnCompletion { EspressoIdlingResource.decrement() }
    return job
}

fun Intent.getCachedImage(context: Context): File {
    val image = createTemporaryFile(context)
    val output = FileOutputStream(image)
    (this?.extras?.get(INTENT_EXTRA_DATA) as Bitmap).let { bitmap ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, BITMAP_QUALITY, output)
        output.flush()
        output.close()
    }
    return image
}

@VisibleForTesting
fun createTemporaryFile(context: Context): File {
    return File.createTempFile(
        "img_",
        ".jpg",
        context.cacheDir
    )
}

fun String.uploadReference(): String {
    return this.split("https://firebasestorage.googleapis.com/v0/b/com-stonetree-imagebucket.appspot.com/o/")[1]
        .split("?").first().replace("%2F", "/")
}

fun String.downloadReference(): String {
    return this.split("https://storage.googleapis.com/com-stonetree-imagebucket.appspot.com/")[1]
        .split("?").first().replace("%2F", "/")
}
