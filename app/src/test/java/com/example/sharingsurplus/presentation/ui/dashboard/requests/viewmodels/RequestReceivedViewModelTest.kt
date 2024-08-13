package com.example.sharingsurplus.presentation.ui.dashboard.requests.viewmodels

import com.example.sharingsurplus.data.model.Produce
import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.requests.RequestReceivedScreenUiState
import com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels.ProfileKarmaPointsViewModel
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import com.example.sharingsurplus.data.repository.Result
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class RequestReceivedViewModelTest {

    private lateinit var viewModel: RequestReceivedViewModel
    private lateinit var authRepository: AuthRepository
    private lateinit var firestoreRepository: FirestoreRepository
    private lateinit var mockUser: FirebaseUser

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        authRepository = mockk()
        firestoreRepository = mockk()
        mockUser = mockk()

        every { authRepository.currentUser } returns mockUser
        every { mockUser.uid } returns "test_uid"

        val request = Request(
            requestId = "request_id",
            produceId = "produce_id",
            ownerId = "owner_id",
            requesterId = "requester_id",
            requestedQuantity = 5
        )
        val produce = mockk<Produce>(relaxed = true)

        coEvery { firestoreRepository.getRequestsForOwner("test_uid") } returns flow{
            emit(listOf(request))
        }
        coEvery { firestoreRepository.respondToRequest(any(), any(), any()) } returns Result.Success(Unit)
        coEvery { firestoreRepository.getProduce("produce_id") } returns Result.Success(produce)
        coEvery { firestoreRepository.updateKarmaPoints(any(), any()) } returns Result.Success(Unit)
        coEvery { firestoreRepository.changeProduceQuantity(any(), any()) } returns Result.Success(Unit)
        coEvery { firestoreRepository.changeProduceStatus(any(), any()) } returns Result.Success(Unit)
        coEvery { firestoreRepository.respondToRequest(any(), any(), any()) } returns Result.Success(Unit)
        coEvery { firestoreRepository.changeProduceStatus(any(), any()) } returns Result.Success(Unit)

        viewModel = RequestReceivedViewModel(firestoreRepository, authRepository)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Dialog visibility changes are handled correctly`() {
        viewModel.onAcceptDialogVisibleChanged(true)
        assertEquals(true, viewModel.requestReceivedUiState.value.isAcceptDialogVisible)

        viewModel.onRejectDialogVisibleChanged(true)
        assertEquals(true, viewModel.requestReceivedUiState.value.isRejectDialogVisible)
    }

    @Test
    fun `Request acceptance updates state and interacts with repositories`() = runTest {
        val request = Request(
            requestId = "request_id",
            produceId = "produce_id",
            ownerId = "owner_id",
            requesterId = "requester_id",
            requestedQuantity = 5
        )
        viewModel.onSelectedRequest(request)
        viewModel.onReasonChanged("Valid reason")
        viewModel.onAcceptRequest()

        assertEquals(Result.Success(Unit), viewModel.requestReceivedUiState.value.acceptResult)
    }

    @Test
    fun `Request rejection updates state and interacts with repositories`() = runTest {
        val request = Request(
            requestId = "request_id",
            produceId = "produce_id",
            ownerId = "owner_id",
            requesterId = "requester_id",
            requestedQuantity = 5
        )

        viewModel.onSelectedRequest(request)
        viewModel.onReasonChanged("Valid reason")
        viewModel.onRejectRequest()

        assertEquals(Result.Success(Unit), viewModel.requestReceivedUiState.value.rejectResult)
    }

    @Test
    fun `Reason validation works correctly`() {
        viewModel.onReasonChanged("")
        assertEquals(false, viewModel.validateReason())

        viewModel.onReasonChanged("Valid reason")
        assertEquals(true, viewModel.validateReason())
    }
}