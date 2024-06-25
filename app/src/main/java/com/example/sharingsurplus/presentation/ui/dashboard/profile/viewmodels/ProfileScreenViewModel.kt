package com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.profile.ProfileScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository
):ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileScreenUiState())
    val profileUiState = _profileUiState.asStateFlow()

    init {
        _profileUiState.value = _profileUiState.value.copy(name = authRepository.currentUser?.displayName!!)//TODO: Get the name from firestore db instead
    }

    fun logout() {
        authRepository.logout()
        _profileUiState.value = _profileUiState.value.copy(isLoggedOut = true)
    }
}