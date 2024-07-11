package com.example.sharingsurplus.data.repository.storage

import android.net.Uri
import com.example.sharingsurplus.data.repository.AuthResult

interface FirebaseStorageRepository {
    suspend fun uploadImageToStorage(uid: String, imageUri: Uri): AuthResult<String>
}