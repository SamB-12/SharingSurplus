package com.example.sharingsurplus.presentation.ui.dashboard.produce.viewmodels

import android.net.Uri
import com.example.sharingsurplus.data.model.Produce
import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.Result
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.repository.storage.FirebaseStorageRepository
import com.example.sharingsurplus.data.states.dashboard.produce.AddProduceUiState
import com.example.sharingsurplus.data.states.status.ProduceType
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddProduceViewModelTest{
    private lateinit var viewModel: AddProduceViewModel
    private lateinit var authRepository: AuthRepository
    private lateinit var firestoreRepository: FirestoreRepository
    private lateinit var firebaseStorageRepository: FirebaseStorageRepository
    private lateinit var mockUser: FirebaseUser


    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        authRepository = mockk()
        firestoreRepository = mockk()
        firebaseStorageRepository = mockk()
        mockUser = mockk()

        every { authRepository.currentUser } returns mockUser

        every { mockUser.uid } returns "test-uid"

        viewModel = AddProduceViewModel(firestoreRepository,firebaseStorageRepository,authRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() {
        val initialState = AddProduceUiState()
        assertEquals(initialState, viewModel.addProduceUiState.value)
    }

    @Test
    fun `onProduceNameChanged updates state correctly`() {
        val newName = "Apples"
        viewModel.onProduceNameChanged(newName)
        assertEquals(newName, viewModel.addProduceUiState.value.produceName)
    }

    @Test
    fun `onProduceQuantityChanged updates state with valid input`() {
        val newQuantity = "5"
        viewModel.onProduceQuantityChanged(newQuantity)
        assertEquals(5, viewModel.addProduceUiState.value.produceQuantity)
    }

    @Test
    fun `onProduceQuantityChanged updates state with invalid input`() {
        val invalidQuantity = "invalid"
        viewModel.onProduceQuantityChanged(invalidQuantity)
        assertEquals(0, viewModel.addProduceUiState.value.produceQuantity)
    }

    @Test
    fun `onLocationChanged updates state correctly`() {
        val newLocation = "St Andrews"
        viewModel.onLocationChanged(newLocation)
        assertEquals(newLocation, viewModel.addProduceUiState.value.produceLocation)
    }

    @Test
    fun `validateInput returns false when required fields are missing`() {
        // Only fill some fields
        viewModel.onProduceNameChanged("Fresh Apples")
        viewModel.onProduceDescriptionChanged("Sweet and crispy apples.")
        viewModel.onProduceTypeChanged(ProduceType.Fruit)

        val isValid = viewModel.validateInput()
        assertFalse(isValid)
    }

    @Test
    fun `uploadProduce updates state with error when fields are missing`() = runTest {
        viewModel.uploadProduce()
        assertTrue(viewModel.addProduceUiState.value.uploadResult is Result.Error)
    }
}