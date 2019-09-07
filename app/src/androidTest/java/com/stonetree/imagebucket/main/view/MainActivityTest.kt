package com.stonetree.imagebucket.main.view

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import kotlinx.android.synthetic.main.activity_main.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test_useAppContext_shouldReturnPackageName() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.stonetree.imagebucket", appContext.packageName)
    }

    @Test
    fun test_bindXml_shouldReturnNothingNull() {
        assertNotNull(rule.activity.gallery.adapter)
    }
}
