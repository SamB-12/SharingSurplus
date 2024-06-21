package com.example.sharingsurplus.data.repository.firestore

import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.AuthResult

interface FirestoreRepository {

    suspend fun getUser(uid:String): AuthResult<User>

    suspend fun updateUser(uid: String, updatedUser: User): AuthResult<User>

    suspend fun createUser(user: User)

    /////////////////////
}