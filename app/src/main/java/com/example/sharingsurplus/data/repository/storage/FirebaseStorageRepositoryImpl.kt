package com.example.sharingsurplus.data.repository.storage

import android.net.Uri
import com.example.sharingsurplus.data.repository.Result
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

/**
 * FirebaseStorageRepositoryImpl is a class that implements the FirebaseStorageRepository interface.
 */
class FirebaseStorageRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) : FirebaseStorageRepository {
    override suspend fun uploadImageToStorage(uid: String, imageUri: Uri): Result<String> {
        return try {
            val storageRef = firebaseStorage.reference
                .child("produce_images/$uid/${UUID.randomUUID()}")
            storageRef.putFile(imageUri).await()
            val downloadUrl = storageRef.downloadUrl.await()
            Result.Success(downloadUrl.toString())
        } catch (e: Exception){
            Result.Error(e.message.toString())
        }
    }
}