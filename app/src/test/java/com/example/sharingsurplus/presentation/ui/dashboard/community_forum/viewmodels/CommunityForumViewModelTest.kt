package com.example.sharingsurplus.presentation.ui.dashboard.community_forum.viewmodels

import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class CommunityForumViewModelTest {

    private lateinit var viewModel: CommunityForumViewModel
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

        every { mockUser.uid } returns "test-uid"

        viewModel = CommunityForumViewModel(
            firestoreRepository = firestoreRepository,
            authRepository = authRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `validateInput should return true when both title and content are non-empty`() {
        val title = "Valid Title"
        val content = "Valid Content"

        val result = viewModel.validateInput(title, content)

        assertTrue(result)
    }

    @Test
    fun `validateInput should return false when title is empty and content is non-empty`() {
        val title = ""
        val content = "Valid Content"

        val result = viewModel.validateInput(title, content)

        assertFalse(result)
    }

    @Test
    fun `validateInput should return false when content is empty and title is non-empty`() {
        val title = "Valid Title"
        val content = ""

        val result = viewModel.validateInput(title, content)

        assertFalse(result)
    }

    @Test
    fun `validateInput should return false when both title and content are empty`() {
        val title = ""
        val content = ""

        val result = viewModel.validateInput(title, content)

        assertFalse(result)
    }

}