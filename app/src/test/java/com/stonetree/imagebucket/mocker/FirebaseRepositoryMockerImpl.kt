package com.stonetree.imagebucket.mocker

import android.net.Uri
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.lang.StringBuilder

open class FirebaseRepositoryMockerImpl : FirebaseRepositoryMocker {
    override fun mockPutFile(storage: FirebaseStorage, path: String) {
        storage.reference.apply {
            `when`(this)
                .thenReturn(mock(StorageReference::class.java))

            `when`(child(path))
                .thenReturn(storage.reference)

            `when`(
                putFile(
                    Uri.parse(path)
                )
            ).thenReturn(mock(UploadTask::class.java))
        }
    }

    override fun mockFirebaseStorage(): FirebaseStorage {
        return mock(FirebaseStorage::class.java)
    }

    override fun mockFirebaseFunctions(): FirebaseFunctions {
        return mock(FirebaseFunctions::class.java)
    }
}