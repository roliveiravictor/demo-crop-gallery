package com.stonetree.imagebucket.core.extensions

import com.stonetree.imagebucket.core.constants.Constants
import com.stonetree.imagebucket.core.constants.Constants.FIREBASE_IMAGES_PATH
import com.stonetree.imagebucket.core.constants.Google.DOWNLOAD_DOMAIN
import com.stonetree.imagebucket.core.constants.Google.DOWNLOAD_PATH
import com.stonetree.imagebucket.core.constants.Google.UPLOAD_DOMAIN
import com.stonetree.imagebucket.core.constants.Google.UPLOAD_PATH
import java.util.*

fun String.uploadReference(): String {
    return split(UPLOAD_DOMAIN + UPLOAD_PATH)[1].parse()
}

fun String.downloadReference(): String {
    return split(DOWNLOAD_DOMAIN + DOWNLOAD_PATH)[1].parse()
}

fun String.referenceHash(): String {
    return this + UUID.randomUUID().toString()
}

private fun String.parse(): String {
    return split("?")
        .first()
        .replace("%2F", "/")
}