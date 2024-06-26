package com.example.sharingsurplus.data.repository.firestore

import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.AuthResult
import com.google.firebase.firestore.ListenerRegistration

interface FirestoreRepository {

    suspend fun getUser(uid:String): AuthResult<User>

    suspend fun updateUser(uid: String, updatedUser: User): AuthResult<User>

    suspend fun createUser(user: User)

    suspend fun getRealTimeUser(uid: String, onUserUpdated: (User) -> Unit): ListenerRegistration

    /////////////////////
}