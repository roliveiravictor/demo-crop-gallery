package com.stonetree.imagebucket.mocker

import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage

interface FirebaseRepositoryMocker {

    fun mockFirebaseStorage(): FirebaseStorage

    fun mockFirebaseFunctions(): FirebaseFunctions

    fun mockPutFile(storage: FirebaseStorage, path: String)
}