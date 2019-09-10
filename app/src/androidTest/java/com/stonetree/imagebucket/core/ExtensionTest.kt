package com.stonetree.imagebucket.core

import android.R
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stonetree.imagebucket.core.constants.Constants.INTENT_EXTRA_DATA
import com.stonetree.imagebucket.core.extensions.createTemporaryFile
import com.stonetree.imagebucket.core.extensions.downloadReference
import com.stonetree.imagebucket.core.extensions.getCachedImage
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExtensionTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun test_createTemporaryFile_ShouldReturnFile() {
        assertNotNull(createTemporaryFile(context))
    }

    @Test
    fun test_cachedImage_ShouldReturnFile() {
        val bitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.arrow_down_float
        )

        val intent = Intent()
        intent.putExtra(INTENT_EXTRA_DATA, bitmap)

        val image = intent.getCachedImage(context)

        assertThat(image, notNullValue())
    }

    @Test
    fun test_fileName_shouldReturnRaw() {

        val url = "https://storage.googleapis.com/" +
                "com-stonetree-imagebucket.appspot.com/" +
                "images%2Fflower-progressive.jpg?" +
                "GoogleAccessId=firebase-adminsdk-vfv2w%40" +
                "com-stonetree-imagebucket.iam.gserviceaccount.com" +
                "&Expires=1567814400&Signature=liv7dT58Ls4SubqYnsIzYYkpM3Zf%" +
                "2B8xtetBmFdkNAM1IOpsFYTeby5GESRGvhj7nspjzXKfLvQFJg6" +
                "0w7VviaFNCJEyCNc3Ez6BOYxsAXPlTKvKWv7KxIk1ySu1lgwPbJTXJJrag3SKe" +
                "%2BKL86wgqPGMir%2FUGMByTwGz0mH3pbTnwoy6EVj%2BKvKgVNI3Ue2rDl4" +
                "jxhoolPlkgQ2m0MW2nKY1npaix6gtth2miii5vluBL0%2Bh0ieWOswEZ3QkQ7s8" +
                "a3bRyMit50ndHjpZOpdmZ8FWT3x6mvmgMPqGVrb0Rvf1jK3pwlP0FAoc91ew375" +
                "BDhT9gPLuUCEZpvL0VD4hMgQ%3D%3D"

        assertEquals("images/flower-progressive.jpg", url.downloadReference())
    }
}
