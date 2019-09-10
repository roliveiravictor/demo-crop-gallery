package com.stonetree.imagebucket.core.extensions

fun String.uploadReference(): String {
    return this.split("https://firebasestorage.googleapis.com" +
            "/v0/b/com-stonetree-imagebucket.appspot.com/o/")[1]
        .split("?")
        .first()
        .replace("%2F", "/")
}

fun String.downloadReference(): String {
    return this.split("https://storage.googleapis.com/" +
            "com-stonetree-imagebucket.appspot.com/")[1]
        .split("?")
        .first()
        .replace("%2F", "/")
}