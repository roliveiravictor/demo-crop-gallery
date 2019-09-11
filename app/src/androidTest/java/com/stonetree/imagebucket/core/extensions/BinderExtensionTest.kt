package com.stonetree.imagebucket.core.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.stonetree.imagebucket.R
import com.stonetree.imagebucket.core.network.NetworkState.Companion.LOADING
import com.stonetree.imagebucket.core.network.NetworkState.Companion.LOADED
import com.stonetree.imagebucket.core.network.NetworkState.Companion.error
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BinderExtensionTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var view: View

    @Before
    fun setup() {
        view = LayoutInflater.from(context).inflate(R.layout.list_item_main, null)
    }

    @Test
    fun test_bindIsGoneOnLoading_shouldReturnVisible() {
        bindIsGone(view, LOADING)
        assertEquals(VISIBLE, view.visibility)
    }

    @Test
    fun test_bindIsGoneOnError_shouldReturnGone() {
        bindIsGone(view, error("mError"))
        assertEquals(GONE, view.visibility)
    }

    @Test
    fun test_bindIsGoneOnLoaded_shouldReturnGone() {
        bindIsGone(view, LOADED)
        assertEquals(GONE, view.visibility)
    }

    @Test
    fun test_bindIsIdleOnSuccess_shouldReturnVisible() {
        bindIsIdle(view, LOADED)
        assertEquals(VISIBLE, view.visibility)
    }

    @Test
    fun test_bindIsIdleOnFailed_shouldReturnVisible() {
        bindIsIdle(view, error("mError"))
        assertEquals(VISIBLE, view.visibility)
    }

    @Test
    fun test_bindIsIdleOnRunning_shouldReturnGone() {
        bindIsIdle(view, LOADING)
        assertEquals(GONE, view.visibility)
    }
}