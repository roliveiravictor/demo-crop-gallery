package com.stonetree.imagebucket.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import android.content.Intent
import android.R
import android.graphics.BitmapFactory
import com.stonetree.imagebucket.core.Constants.INTENT_EXTRA_DATA
import junit.framework.TestCase
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat


@RunWith(AndroidJUnit4::class)
class ExtensionTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun test_createTemporaryFile_ShouldReturnFile() {
        assertNotNull(createTemporaryFile(context))
    }

    @Test
    fun test_cachedImage_ShouldReturnFile() {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.arrow_down_float)

        val intent = Intent()
        intent.putExtra(INTENT_EXTRA_DATA, bitmap)

        val image = intent.getCachedImage(context)

        assertThat(image, notNullValue())
    }


    @Test
    fun test_fileName_shouldReturnRaw() {
        val url = "https://storage.googleapis.com/com-stonetree-imagebucket.appspot.com/images%2Fflower-progressive.jpg?GoogleAccessId=firebase-adminsdk-vfv2w%40com-stonetree-imagebucket.iam.gserviceaccount.com&Expires=1567814400&Signature=liv7dT58Ls4SubqYnsIzYYkpM3Zf%2B8xtetBmFdkNAM1IOpsFYTeby5GESRGvhj7nspjzXKfLvQFJg60w7VviaFNCJEyCNc3Ez6BOYxsAXPlTKvKWv7KxIk1ySu1lgwPbJTXJJrag3SKe%2BKL86wgqPGMir%2FUGMByTwGz0mH3pbTnwoy6EVj%2BKvKgVNI3Ue2rDl4jxhoolPlkgQ2m0MW2nKY1npaix6gtth2miii5vluBL0%2Bh0ieWOswEZ3QkQ7s8a3bRyMit50ndHjpZOpdmZ8FWT3x6mvmgMPqGVrb0Rvf1jK3pwlP0FAoc91ew375BDhT9gPLuUCEZpvL0VD4hMgQ%3D%3D"
        TestCase.assertEquals("images/flower-progressive.jpg", url.downloadReference())
    }

}