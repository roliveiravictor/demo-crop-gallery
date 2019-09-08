package com.stonetree.imagebucket.main.view.adapter

import android.net.Uri
import com.stonetree.imagebucket.main.model.MainModel
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class MainViewHolderTest {

    private val holder = mock(MainViewHolder::class.java)

    @Test
    fun test_onBind_ShouldReturnSame() {
        val uri = mock(Uri::class.java)
        val model = MainModel(uri, "mName")

        holder.onBind(model)

        verify(holder).onBind(model)

        assertEquals(uri, model.imageUrl)
        assertEquals("mName", model.imageName)
    }
}