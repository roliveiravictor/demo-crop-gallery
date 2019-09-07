package com.stonetree.imagebucket

fun <T : Any> T.accessField(fieldName: String): Any? {
    return this?.javaClass?.getDeclaredField(fieldName).let { field ->
        field?.isAccessible = true
        return@let field?.get(this)
    }
}
