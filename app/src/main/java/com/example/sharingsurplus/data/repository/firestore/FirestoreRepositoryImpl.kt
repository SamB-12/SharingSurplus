package com.example.sharingsurplus.data.repository.firestore

import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.AuthResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore : FirebaseFirestore
) : FirestoreRepository {
    override suspend fun getUser(uid: String): AuthResult<User> {
        return try {
            val document = firestore.collection("users")
                .document(uid)
                .get()
                .await()

            if (document.exists()){
                val user = document.toObject(User::class.java)
                AuthResult.Success(user!!)
            } else {
                AuthResult.Error("User not found")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun updateUser(uid: String, updatedUser: User): AuthResult<User> {
        val userToUpdate = hashMapOf(
            "name" to updatedUser.name,
            "email" to updatedUser.email,
            "phone" to updatedUser.phone,
            "address" to updatedUser.address
        )

       return try {
            firestore.collection("users")
                .document(uid)
                .set(userToUpdate, SetOptions.merge())
                .await()

            AuthResult.Success(updatedUser)
        } catch (e: Exception) {
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun createUser(user: User) {
        val userToCreate = hashMapOf(
            "uid" to user.uid,
            "name" to user.name,
            "email" to user.email,
            "createdAt" to user.createdAt
        )

        firestore.collection("users")
            .document(user.uid)
            .set(userToCreate, SetOptions.merge())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    println("User added to firestore")
                } else {
                    println("Failed to add user to firestore")
                }
            }
    }
}