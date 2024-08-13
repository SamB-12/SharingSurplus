package com.example.sharingsurplus.data.repository.auth

import com.example.sharingsurplus.data.repository.Result
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class AuthRepositoryImplTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authRepository: AuthRepositoryImpl
    private lateinit var firestoreRepository: FirestoreRepository

    @Before
    fun setUp() {

        firebaseAuth = mockk()
        firestoreRepository = mockk()
        authRepository = AuthRepositoryImpl(firebaseAuth, firestoreRepository)
    }

    @Test
    fun `login should return success when firebase auth is successful`() = runBlocking {
        val mockUser: FirebaseUser = mockk()
        val mockAuthResult: AuthResult = mockk()
        val mockTask: Task<AuthResult> = mockk()

        every { firebaseAuth.signInWithEmailAndPassword(any(), any()) } returns mockTask
        every { mockTask.isSuccessful } returns true
        every { mockTask.result } returns mockAuthResult
        every { mockAuthResult.user } returns mockUser
        every { firebaseAuth.currentUser } returns mockUser

        val slot = slot<OnCompleteListener<AuthResult>>()
        every { mockTask.addOnCompleteListener(capture(slot)) } answers {
            slot.captured.onComplete(mockTask)
            mockTask
        }

        val result = authRepository.login("test@example.com", "password")

        // Assert that the result is a success and the correct user is returned
        assertTrue(result is Result.Success)
        assertEquals(mockUser, (result as Result.Success).data)
    }

    @Test
    fun `login should return error when firebase auth fails`() = runBlocking {
        // Mock Task<AuthResult>
        val mockTask: Task<AuthResult> = mockk()

        every { firebaseAuth.signInWithEmailAndPassword(any(), any()) } returns mockTask
        every { mockTask.isSuccessful } returns false
        every { mockTask.exception } returns Exception("Login failed")

        // Capture the OnCompleteListener and trigger it manually
        val slot = slot<OnCompleteListener<AuthResult>>()
        every { mockTask.addOnCompleteListener(capture(slot)) } answers {
            slot.captured.onComplete(mockTask)
            mockTask
        }

        val result = authRepository.login("test@example.com", "password")

        // Assert that the result is an error and the correct message is returned
        assertTrue(result is Result.Error)
        assertEquals("Login failed", (result as Result.Error).message)
    }
}