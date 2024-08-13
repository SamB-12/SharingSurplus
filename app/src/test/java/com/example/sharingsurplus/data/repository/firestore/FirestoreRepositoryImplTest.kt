package com.example.sharingsurplus.data.repository.firestore

import com.example.sharingsurplus.data.model.Post
import com.example.sharingsurplus.data.model.Produce
import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.states.status.ProduceStatus
import com.example.sharingsurplus.data.states.status.RequestStatus
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import com.example.sharingsurplus.data.repository.Result
import com.example.sharingsurplus.data.repository.auth.AuthRepositoryImpl
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class FirestoreRepositoryImplTest {

    private lateinit var mockFirestore: FirebaseFirestore
    private lateinit var firestoreRepository: FirestoreRepositoryImpl

    @Before
    fun setUp() {
        mockFirestore = mockk()
        firestoreRepository = FirestoreRepositoryImpl(mockFirestore)
    }

    @Test
    fun `getUser should return success when query is successful`() = runBlocking {
        val uid = "testUid"
        val mockUser = User(uid = uid, name = "Test User", email = "test@example.com")
        val mockDocumentSnapshot: DocumentSnapshot = mockk()
        val mockDocumentReference: DocumentReference = mockk()

        every { mockFirestore.collection("users").document(uid) } returns mockDocumentReference
        coEvery { mockDocumentReference.get().await() } returns mockDocumentSnapshot
        every { mockDocumentSnapshot.exists() } returns true
        every { mockDocumentSnapshot.toObject(User::class.java) } returns mockUser

        val result = firestoreRepository.getUser(uid)

        assertTrue(result is Result.Success)
        assertEquals(mockUser, (result as Result.Success).data)
    }

    @Test
    fun `getUser should return error when query fails`() = runBlocking {
        val uid = "testUid"
        val mockDocumentReference: DocumentReference = mockk()

        every { mockFirestore.collection("users").document(uid) } returns mockDocumentReference
        coEvery { mockDocumentReference.get().await() } throws Exception("Firestore query failed")

        val result = firestoreRepository.getUser(uid)

        assertTrue(result is Result.Error)
        assertEquals("Firestore query failed", (result as Result.Error).message)
    }

    @Test
    fun `getUser should return error when user does not exist`() = runBlocking {
        val uid = "testUid"
        val mockDocumentSnapshot: DocumentSnapshot = mockk()
        val mockDocumentReference: DocumentReference = mockk()

        every { mockFirestore.collection("users").document(uid) } returns mockDocumentReference
        coEvery { mockDocumentReference.get().await() } returns mockDocumentSnapshot
        every { mockDocumentSnapshot.exists() } returns false

        val result = firestoreRepository.getUser(uid)

        assertTrue(result is Result.Error)
        assertEquals("User not found", (result as Result.Error).message)
    }
}