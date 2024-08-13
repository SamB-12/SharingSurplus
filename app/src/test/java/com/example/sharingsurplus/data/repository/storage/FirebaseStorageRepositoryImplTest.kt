package com.example.sharingsurplus.data.repository.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import com.example.sharingsurplus.data.repository.Result
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FirebaseStorageRepositoryImplTest{
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var storageRepository: FirebaseStorageRepositoryImpl

    @Before
    fun setUp() {
        firebaseStorage = mockk()
        storageRepository = FirebaseStorageRepositoryImpl(firebaseStorage)
    }

    @Test
    fun `uploadImageToStorage should return success when image uploaded`() = runBlocking {
        val uid = "testUid"
        val mockUri: Uri = mockk()
        val mockStorageReference: StorageReference = mockk()
        val mockUploadTask: UploadTask.TaskSnapshot = mockk()
        val mockDownloadUrl: Uri = mockk()

        every { firebaseStorage.reference } returns mockStorageReference
        every { mockStorageReference.child(any()) } returns mockStorageReference
        coEvery { mockStorageReference.putFile(mockUri).await() } returns mockUploadTask
        coEvery { mockStorageReference.downloadUrl.await() } returns mockDownloadUrl
        every { mockDownloadUrl.toString() } returns "https://example.com/mockDownloadUrl"

        val result = storageRepository.uploadImageToStorage(uid, mockUri)

        assertTrue(result is Result.Success)
        assertEquals("https://example.com/mockDownloadUrl", (result as Result.Success).data)
    }

    @Test
    fun `uploadImageToStorage should return error when upload fails`() = runBlocking {
        val uid = "testUid"
        val mockUri: Uri = mockk()
        val mockStorageReference: StorageReference = mockk()

        every { firebaseStorage.reference } returns mockStorageReference
        every { mockStorageReference.child(any()) } returns mockStorageReference
        coEvery { mockStorageReference.putFile(mockUri).await() } throws Exception("Upload failed")

        val result = storageRepository.uploadImageToStorage(uid, mockUri)

        assertTrue(result is Result.Error)
        assertEquals("Upload failed", (result as Result.Error).message)
    }
}