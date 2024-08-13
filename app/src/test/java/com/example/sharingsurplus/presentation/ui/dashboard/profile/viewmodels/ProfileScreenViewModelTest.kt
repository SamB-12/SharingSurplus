package com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels

import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.profile.ProfileKarmaPointsScreenUiState
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import com.example.sharingsurplus.data.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProfileScreenViewModelTest{
    private lateinit var viewModel: ProfileKarmaPointsViewModel
    private lateinit var authRepository: AuthRepository
    private lateinit var firestoreRepository: FirestoreRepository
    private lateinit var mockUser: FirebaseUser

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        authRepository = mockk()
        firestoreRepository = mockk()
        mockUser = mockk()

        every { authRepository.currentUser } returns mockUser
        every { mockUser.uid } returns "test_uid"
        viewModel = ProfileKarmaPointsViewModel(firestoreRepository, authRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Initial state is empty`() = runBlocking {
        val initialState = viewModel.karmaPointsUiState.first()
        assertEquals(ProfileKarmaPointsScreenUiState(), initialState)
    }

    @Test
    fun `State updates with correct karma points on success`() = runBlocking {
        val user = User(karmaPoints = 10)
        coEvery { firestoreRepository.getUser("test_uid") } returns Result.Success(user)

        viewModel = ProfileKarmaPointsViewModel(firestoreRepository, authRepository)

        val updatedState = viewModel.karmaPointsUiState.first()
        assertEquals(10, updatedState.karmaPoints)
    }

    @Test
    fun `State remains empty on user fetch failure`() = runBlocking {
        coEvery { firestoreRepository.getUser("test_uid") } returns Result.Error("Error fetching user")

        viewModel = ProfileKarmaPointsViewModel(firestoreRepository, authRepository)

        val state = viewModel.karmaPointsUiState.first()
        assertEquals(ProfileKarmaPointsScreenUiState(), state)
    }
}