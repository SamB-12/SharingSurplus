package com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.repository.Result
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.profile.ProfileKarmaPointsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This view model is used to get the karma points of the current user.
 */
@HiltViewModel
class ProfileKarmaPointsViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val authRepository: AuthRepository
) : ViewModel()
{

    private val _karmaPointsUiState = MutableStateFlow(ProfileKarmaPointsScreenUiState())
    val karmaPointsUiState = _karmaPointsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            val result = firestoreRepository.getUser(authRepository.currentUser!!.uid)

            if (result is Result.Success){
                val user = result.data
                _karmaPointsUiState.value = ProfileKarmaPointsScreenUiState(
                    user.karmaPoints
                )
            }
        }
    }
}