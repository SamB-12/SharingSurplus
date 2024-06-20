package com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sharingsurplus.data.repository.AuthRepository
import com.example.sharingsurplus.data.states.dashboard.profile.ProfileInfoScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileInfoScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel() {

    private val _profileInfoUiState = MutableStateFlow(ProfileInfoScreenUiState())
    val profileInfoUiState = _profileInfoUiState.asStateFlow()

    init {//change it to info from the firestore!
        _profileInfoUiState.value = _profileInfoUiState.value.copy(name = authRepository.currentUser?.displayName.toString(), email = authRepository.currentUser?.email.toString())
    }
}