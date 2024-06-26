package com.example.sharingsurplus.presentation.ui.dashboard.home.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.states.dashboard.home.HomeScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenUiState())
    val homeScreenUiState = _homeScreenState.asStateFlow()

    init {
        _homeScreenState.value =homeScreenUiState.value.copy(name = authRepository.currentUser?.displayName.toString())
    }

}