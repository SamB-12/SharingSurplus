package com.example.sharingsurplus.presentation.ui.dashboard.produce.viewmodels

import com.example.sharingsurplus.data.model.Produce
import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.presentation.ui.dashboard.community_forum.viewmodels.CommunityForumViewModel
import com.example.sharingsurplus.presentation.utils.GlobalVariables
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import com.example.sharingsurplus.data.repository.Result
import com.example.sharingsurplus.data.states.status.ProduceStatus
import com.example.sharingsurplus.data.states.status.ProduceType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class ViewAndRequestViewModelTest {

    private lateinit var viewModel: ViewAndRequestViewModel
    private lateinit var authRepository: AuthRepository
    private lateinit var firestoreRepository: FirestoreRepository
    private lateinit var mockUser: FirebaseUser


    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        val mockedProduce = Produce(
            produceId = "some-produce-id",
            produceImageUrl = "mockedImageUrl",
            ownerId = "mockedOwnerId",
            produceName = "mockedProduceName",
            produceDescription = "mockedDescription",
            produceType = ProduceType.Fruit,
            produceQuantity = 10,
            produceUnit = "kg",
            producePickupInstructions = "mockedInstructions",
            produceBestBeforeDate = "2024-12-31",
            producerName = "mockedProducerName",
            produceLocation = "mockedLocation",
            produceLatitude = 0.0,
            produceLongitude = 0.0
        )
        Dispatchers.setMain(testDispatcher)
        authRepository = mockk()
        firestoreRepository = mockk()
        mockUser = mockk()

        every { authRepository.currentUser } returns mockUser

        every { mockUser.uid } returns "test-uid"

        coEvery { firestoreRepository.getProduce(any()) } returns Result.Success(mockedProduce)

        coEvery { firestoreRepository.getUser(any()) } returns Result.Success(User("requester-id", "john doe"))
        coEvery { firestoreRepository.createRequest(any()) } returns Result.Success(Unit)
        coEvery { firestoreRepository.changeProduceStatus(any(), any()) } returns Result.Success(Unit)

        viewModel = ViewAndRequestViewModel(firestoreRepository = firestoreRepository, authRepository = authRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should set an initial state and call getProduce`() = runTest {
        assertEquals("mockedProduceName", viewModel.viewAndRequestUiState.value.produceName)
        assertEquals("mockedOwnerId", viewModel.viewAndRequestUiState.value.ownerId)
    }

    @Test
    fun `validateInput returns true when all fields are filled`() {
        viewModel.onPickUpDateChange("2023-12-25")
        viewModel.onPickUpTimeChange("10:00 AM")
        viewModel.onPickUpRequirementsChange("Bring a bag")
        viewModel.onRequestedQuantityChange("5")

        val result = viewModel.validateInput()

        assertTrue(result)
    }

    @Test
    fun `validateInput returns false when required fields are missing`() {
        viewModel.onPickUpDateChange("")
        viewModel.onPickUpTimeChange("")
        viewModel.onRequestedQuantityChange("0")

        val result = viewModel.validateInput()

        assertFalse(result)
    }

    @Test
    fun `validateRequestedQuantity returns false when requested quantity exceeds produce quantity`() {
        viewModel.onRequestedQuantityChange("15") // Let's say 10 units requested

        val result = viewModel.validateRequestedQuantity()

        assertFalse(result)
    }

    @Test
    fun `validateRequestedQuantity returns true when requested quantity is within produce quantity`() {
        viewModel.onRequestedQuantityChange("5")

        val result = viewModel.validateRequestedQuantity()

        assertTrue(result)
    }

    @Test
    fun `onPickUpDateChange should update producePickUpDate in UI state`() {
        val newDate = "2024-12-31"

        viewModel.onPickUpDateChange(newDate)

        assert(viewModel.viewAndRequestUiState.value.producePickUpDate == newDate)
    }

    @Test
    fun `requestProduce should create request successfully when inputs are valid`() = runTest {

        viewModel.onPickUpDateChange("2024-12-31")
        viewModel.onPickUpTimeChange("10:00 AM")
        viewModel.onPickUpRequirementsChange("Bring your own bag")
        viewModel.onRequestedQuantityChange("5")

        viewModel.requestProduce()

        assert(viewModel.viewAndRequestUiState.value.requestResult is Result.Success)
        coVerify { firestoreRepository.createRequest(any()) }
        coVerify { firestoreRepository.changeProduceStatus(any(), ProduceStatus.Requested) }
    }
}