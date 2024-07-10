package com.example.sharingsurplus.data.repository.firestore

import android.util.Log
import com.example.sharingsurplus.data.model.Produce
import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.AuthResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.time.Year
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

            if (document.exists()) {
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

    override suspend fun getRealTimeUser(
        uid: String,
        onUserUpdated: (User) -> Unit
    ): ListenerRegistration {
        return  firestore.collection("users")
                .document(uid)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.e("FirestoreRepository", "Error getting user: ", error)
                        return@addSnapshotListener
                    } else {
                        if (value != null && value.exists()) {
                            val user = value.toObject(User::class.java)
                            if (user != null) {
                                onUserUpdated(user)
                            }
                        } else {
                            Log.e("FirestoreRepository", "User not found")
                        }
                    }
                }

    }

    override suspend fun addProduce(produce: Produce, uid: String): AuthResult<Unit> {
        return try {
            val produceId = firestore.collection("produce").document().id
            val newProduce = produce.copy(produceId = produceId, ownerId = uid)
            Log.d("FirestoreRepository", "addProduce: $newProduce")
            firestore.collection("produce").document(produceId).set(newProduce).await()
            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun getProduceList(): Flow<List<Produce>> = flow{
        val produceList = firestore.collection("produce")
            .get()
            .await()
            .toObjects(Produce::class.java)
        emit(produceList)
    }
}