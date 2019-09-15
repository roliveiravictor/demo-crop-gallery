package com.stonetree.imagebucket.main.view

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ActivityScenario
import junit.framework.TestCase.assertEquals
import org.koin.test.AutoCloseKoinTest

@RunWith(RobolectricTestRunner::class)
class MainActivityTest : AutoCloseKoinTest() {

    @Test
    fun test() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                assertEquals("com.stonetree.imagebucket", activity.packageName)
            }
        }
    }
}