package com.example.sharingsurplus.data.repository.storage

import android.net.Uri
import com.example.sharingsurplus.data.repository.AuthResult
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class FirebaseStorageRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) : FirebaseStorageRepository {
    override suspend fun uploadImageToStorage(uid: String, imageUri: Uri): AuthResult<String> {
        return try {
            val storageRef = firebaseStorage.reference
                .child("produce_images/$uid/${UUID.randomUUID()}")
            storageRef.putFile(imageUri).await()
            val downloadUrl = storageRef.downloadUrl.await()
            AuthResult.Success(downloadUrl.toString())
        } catch (e: Exception){
            AuthResult.Error(e.message.toString())
        }
    }
}