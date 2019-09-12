package com.stonetree.imagebucket.core.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.stonetree.imagebucket.core.constants.Constants.INTENT_EXTRA_DATA
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ImageExtensionsTest {

    private val intent = mock(Intent::class.java)

    private val context = mock(Context::class.java)

    @Test
    fun test_getCachedImageWithNullExtra_shouldReturnFile() {
        assertNotNull(intent.getCachedImage(context))
    }

    @Test
    fun test_getCachedImageWithExtra_shouldReturnFile() {
        val bmp = mock(Bitmap::class.java)

        val bundle = mock(Bundle::class.java)
        `when`(bundle.get(INTENT_EXTRA_DATA)).thenReturn(bmp)

        `when`(intent.extras).thenReturn(bundle)

        assertNotNull(intent.getCachedImage(context))
    }
}