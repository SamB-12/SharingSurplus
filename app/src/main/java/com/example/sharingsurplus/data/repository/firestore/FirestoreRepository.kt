package com.example.sharingsurplus.data.repository.firestore

import com.example.sharingsurplus.data.model.Produce
import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.states.status.ProduceStatus
import com.example.sharingsurplus.data.states.status.RequestStatus
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    suspend fun getUser(uid:String): AuthResult<User>

    suspend fun updateUser(uid: String, updatedUser: User): AuthResult<User>

    suspend fun createUser(user: User)
    suspend fun updateKarmaPoints(uid: String, points: Int): AuthResult<Unit>

    suspend fun getRealTimeUser(uid: String, onUserUpdated: (User) -> Unit): ListenerRegistration

    /////////////////////

    suspend fun addProduce(produce: Produce, uid: String): AuthResult<Unit>

    suspend fun getProduceList(): Flow<List<Produce>>

    suspend fun getProduce(produceId: String): AuthResult<Produce>

    suspend fun updateProduce(produceId: String, updatedProduce: Produce): AuthResult<Unit>
    suspend fun changeProduceStatus(produceId: String, status: ProduceStatus): AuthResult<Unit>
    suspend fun changeProduceQuantity(produceId: String, quantity: Int): AuthResult<Unit>

    suspend fun deleteProduce(produceId: String): AuthResult<Unit>

    /////////////////////

    suspend fun createRequest(produceRequest: Request): AuthResult<Unit>
    suspend fun getRequest(requestId:String): AuthResult<Request>
    suspend fun getRequestsForOwner(ownerId: String): Flow<List<Request>>
    suspend fun getRequestsForRequester(requesterId: String): Flow<List<Request>>
    suspend fun updateRequest(requestId: String, pickUpDate: String, pickUpTime: String, requirements:String, requestedQuantity: Int): AuthResult<Unit>
    suspend fun deleteRequest(requestId: String): AuthResult<Unit>
    suspend fun respondToRequest(requestId: String, status: RequestStatus, response: String): AuthResult<Unit>


}