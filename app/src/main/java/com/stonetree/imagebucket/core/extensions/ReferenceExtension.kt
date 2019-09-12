package com.stonetree.imagebucket.core.extensions

import com.stonetree.imagebucket.core.constants.Google.DOWNLOAD_DOMAIN
import com.stonetree.imagebucket.core.constants.Google.DOWNLOAD_PATH
import com.stonetree.imagebucket.core.constants.Google.UPLOAD_DOMAIN
import com.stonetree.imagebucket.core.constants.Google.UPLOAD_PATH

fun String.uploadReference(): String {
    return split(UPLOAD_DOMAIN + UPLOAD_PATH)[1].parse()
}

fun String.downloadReference(): String {
    return split(DOWNLOAD_DOMAIN + DOWNLOAD_PATH)[1].parse()
}

private fun String.parse(): String {
    return split("?")
        .first()
        .replace("%2F", "/")
}