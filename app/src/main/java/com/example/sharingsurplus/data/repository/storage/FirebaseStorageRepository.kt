package com.example.sharingsurplus.data.repository.storage

import android.net.Uri
import com.example.sharingsurplus.data.repository.Result

/**
 * Interface for the Firebase Storage repository.
 */
interface FirebaseStorageRepository {
    suspend fun uploadImageToStorage(uid: String, imageUri: Uri): Result<String>
}