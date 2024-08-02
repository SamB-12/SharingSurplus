package com.example.sharingsurplus.data.repository.firestore

import android.util.Log
import com.example.sharingsurplus.data.model.Post
import com.example.sharingsurplus.data.model.Produce
import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.states.status.ProduceStatus
import com.example.sharingsurplus.data.states.status.RequestStatus
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.rpc.context.AttributeContext.Auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    override suspend fun updateKarmaPoints(uid: String, points: Int): AuthResult<Unit> {
        return try {
            val document = firestore.collection("users")
                .document(uid)
                .get()
                .await()

            if (document.exists()) {
                val currentKarma = document.getLong("karmaPoints")?.toInt() ?: 0
                val newKarma = currentKarma + points
                val updatedKarma = hashMapOf(
                    "karmaPoints" to newKarma
                )
                firestore.collection("users")
                    .document(uid)
                    .set(updatedKarma, SetOptions.merge())
                    .await()
                AuthResult.Success(Unit)
            } else {
                AuthResult.Error("User not found")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message.toString())
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

//    override suspend fun getProduceList(): Flow<List<Produce>> = flow{
//        val produceList = firestore.collection("produce")
//            .get()
//            .await()
//            .toObjects(Produce::class.java)
//        emit(produceList)
//    }

    override suspend fun getProduceList(): Flow<List<Produce>> = callbackFlow {
        val listenerRegistration = firestore.collection("produce")
            .addSnapshotListener() { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                } else {
                    val produceList = value?.toObjects(Produce::class.java)
                    if (produceList != null) {
                        trySend(produceList).isSuccess
                    }
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }

    override suspend fun getProduce(produceId: String): AuthResult<Produce> {
        return try {
            val document = firestore.collection("produce")
                .document(produceId)
                .get()
                .await()

            if (document.exists()) {
                val produce = document.toObject(Produce::class.java)
                AuthResult.Success(produce!!)
            } else {
                AuthResult.Error("User not found")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun updateProduce(
        produceId: String,
        updatedProduce: Produce
    ): AuthResult<Unit> {

        return try {

            firestore.collection("produce")
                .document(produceId)
                .set(updatedProduce, SetOptions.merge())
                .await()

            AuthResult.Success(Unit)
        } catch (e: Exception){
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun changeProduceStatus(
        produceId: String,
        status: ProduceStatus
    ): AuthResult<Unit> {
        val changedStatus = hashMapOf(
            "produceStatus" to status
        )
        return try {
            firestore.collection("produce")
                .document(produceId)
                .set(changedStatus, SetOptions.merge())
                .await()
            AuthResult.Success(Unit)
        } catch (e: Exception){
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun changeProduceQuantity(produceId: String, quantity: Int): AuthResult<Unit> {
        val changeQuantity = hashMapOf(
            "produceQuantity" to quantity
        )
        return try {
            firestore.collection("produce")
                .document(produceId)
                .set(changeQuantity, SetOptions.merge())
                .await()
            AuthResult.Success(Unit)
        } catch (e: Exception){
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun deleteProduce(produceId: String): AuthResult<Unit> {
        return try {
            val document = firestore.collection("produce")
                .document(produceId)
                .get().await()

            if (document.exists()){
                firestore.collection("produce")
                    .document(produceId).delete().await()
                AuthResult.Success(Unit)
            } else{
                AuthResult.Error("Produce not found")
            }
        } catch (e: Exception){
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun createRequest(produceRequest: Request): AuthResult<Unit> {
        return try {
            val requestId = firestore.collection("requests").document().id
            val newRequest = produceRequest.copy(requestId = requestId)
            firestore.collection("requests").document(requestId).set(newRequest).await()
            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun getRequest(requestId: String): AuthResult<Request> {
        return try {
            val document = firestore.collection("requests")
                .document(requestId)
                .get()
                .await()

            if (document.exists()) {
                val request = document.toObject(Request::class.java)
                AuthResult.Success(request!!)
            } else {
                AuthResult.Error("Request not found")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun getRequestsForOwner(ownerId: String): Flow<List<Request>> = callbackFlow{
        val listenerRegistration = firestore.collection("requests")
            .whereEqualTo("ownerId", ownerId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val requests = value?.toObjects(Request::class.java)
                Log.d("FirestoreRepository", "getRequestsForOwner: $requests")
                if (requests != null) {
                    trySend(requests).isSuccess
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }

    override suspend fun getRequestsForRequester(requesterId: String): Flow<List<Request>> = callbackFlow {
        val listenerRegistration = firestore.collection("requests")
            .whereEqualTo("requesterId", requesterId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val requests = value?.toObjects(Request::class.java)
                Log.d("FirestoreRepository", "getRequestsForOwner: $requests")
                if (requests != null) {
                    trySend(requests).isSuccess
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }

    override suspend fun updateRequest(
        requestId: String,
        pickUpDate: String,
        pickUpTime: String,
        requirements:String,
        requestedQuantity: Int
    ): AuthResult<Unit> {
        val updatedRequest = hashMapOf(
            "requestedTime" to pickUpTime,
            "requestedDate" to pickUpDate,
            "requestedQuantity" to requestedQuantity,
            "requestInstructions" to requirements
        )

        return try {
            firestore.collection("requests").document(requestId)
                .set(updatedRequest, SetOptions.merge()).await()
            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun deleteRequest(requestId: String): AuthResult<Unit> {
        return try {
            firestore.collection("requests").document(requestId).delete().await()
            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun respondToRequest(requestId: String, status: RequestStatus, response: String): AuthResult<Unit> {
        val responseRequest = hashMapOf(
            "reason" to response,
            "status" to status
        )
        return try {
            firestore.collection("requests").document(requestId)
                .set(responseRequest, SetOptions.merge()).await()
            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun addPost(post: Post): AuthResult<Unit> {
        return try {
            val postId = firestore.collection("posts").document().id
            val newPost = post.copy(postId = postId)
            firestore.collection("posts").document(postId).set(newPost).await()
            AuthResult.Success(Unit)
        } catch (e:Exception){
            AuthResult.Error(e.message.toString())
        }
    }

    override suspend fun getPosts(): Flow<List<Post>> = callbackFlow{
        val listenerRegistration = firestore.collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener{ value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (value != null && !value.isEmpty) {
                    val posts = value.toObjects(Post::class.java)
                    trySend(posts).isSuccess
                }
            }
        awaitClose {
            listenerRegistration.remove()
        }
    }
}