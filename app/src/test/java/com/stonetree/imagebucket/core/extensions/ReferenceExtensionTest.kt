package com.stonetree.imagebucket.core.extensions

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import org.junit.Test
import java.lang.StringBuilder

class ReferenceExtensionTest {

    private val fileName = "images%2Fflower-progressive.jpg"
    private val params = "?GoogleAccessId=firebase-adminsdk-vfv2w%40"

    @Test
    fun test_downloadReference_shouldReturnName() {
        val domain = "https://storage.googleapis.com"
        val path = "/com-stonetree-imagebucket.appspot.com/"

        assertEquals(
            "images/flower-progressive.jpg",
            createUrl(domain, path).downloadReference()
        )
    }

    @Test
    fun test_uploadReference_shouldReturnName() {
        val domain = "https://firebasestorage.googleapis.com"
        val path = "/v0/b/com-stonetree-imagebucket.appspot.com/o/"

        assertEquals(
            "images/flower-progressive.jpg",
            createUrl(domain, path).uploadReference()
        )
    }

    @Test
    fun test_referenceHash_shouldReturnDifferent() {
        val id = "mRef"
        assertNotSame(id, id.referenceHash())
    }

    @Test
    fun test_referenceHash_shouldReturnUniqueId() {
        val id = "mRef"
        val hashOne = id.referenceHash()
        val hashTwo = id.referenceHash()
        assertNotSame(hashOne, hashTwo)
    }

    private fun createUrl(domain: String, path: String): String {
        return StringBuilder().append(domain)
            .append(path)
            .append(fileName)
            .append(params)
            .toString()
    }
}