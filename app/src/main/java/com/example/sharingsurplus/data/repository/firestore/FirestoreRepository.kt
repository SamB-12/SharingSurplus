package com.example.sharingsurplus.data.repository.firestore

import com.example.sharingsurplus.data.model.Post
import com.example.sharingsurplus.data.model.Produce
import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.Result
import com.example.sharingsurplus.data.states.status.ProduceStatus
import com.example.sharingsurplus.data.states.status.RequestStatus
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.Flow

/**
 * This interface defines the contract for interacting with the Firestore database.
 */
interface FirestoreRepository {

    suspend fun getUser(uid:String): Result<User>

    suspend fun updateUser(uid: String, updatedUser: User): Result<User>

    suspend fun createUser(user: User)
    suspend fun updateKarmaPoints(uid: String, points: Int): Result<Unit>

    suspend fun getRealTimeUser(uid: String, onUserUpdated: (User) -> Unit): ListenerRegistration

    /////////////////////

    suspend fun addProduce(produce: Produce, uid: String): Result<Unit>

    suspend fun getProduceList(): Flow<List<Produce>>

    suspend fun getProduce(produceId: String): Result<Produce>

    suspend fun updateProduce(produceId: String, updatedProduce: Produce): Result<Unit>
    suspend fun changeProduceStatus(produceId: String, status: ProduceStatus): Result<Unit>
    suspend fun changeProduceQuantity(produceId: String, quantity: Int): Result<Unit>

    suspend fun deleteProduce(produceId: String): Result<Unit>

    /////////////////////

    suspend fun createRequest(produceRequest: Request): Result<Unit>
    suspend fun getRequest(requestId:String): Result<Request>
    suspend fun getRequestsForOwner(ownerId: String): Flow<List<Request>>
    suspend fun getRequestsForRequester(requesterId: String): Flow<List<Request>>
    suspend fun updateRequest(requestId: String, pickUpDate: String, pickUpTime: String, requirements:String, requestedQuantity: Int): Result<Unit>
    suspend fun deleteRequest(requestId: String): Result<Unit>
    suspend fun respondToRequest(requestId: String, status: RequestStatus, response: String): Result<Unit>

    /////////////////////

    suspend fun addPost(post: Post): Result<Unit>

    suspend fun getPosts(): Flow<List<Post>>

}