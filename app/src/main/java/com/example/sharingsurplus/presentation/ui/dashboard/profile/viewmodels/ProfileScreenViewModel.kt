package com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.repository.Result
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.profile.ProfileScreenUiState
import com.example.sharingsurplus.presentation.utils.GlobalVariables
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This is the view model for the profile screen.
 */
@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository
):ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileScreenUiState())
    val profileUiState = _profileUiState.asStateFlow()

    var userUpdateListenerRegistration: ListenerRegistration? = null

    init {
        viewModelScope.launch {
            val result = firestoreRepository.getUser(authRepository.currentUser?.uid!!)
            GlobalVariables.ownerId = authRepository.currentUser?.uid!!

            if (result is Result.Success){
                val user = result.data
                _profileUiState.value = _profileUiState.value.copy(name = user.name)
                setupUpdateListener(authRepository.currentUser?.uid!!)
            }
        }
    }

    suspend fun setupUpdateListener(uid: String){
        userUpdateListenerRegistration = firestoreRepository.getRealTimeUser(uid){user ->
            _profileUiState.value = _profileUiState.value.copy(name = user.name)
        }
    }

    override fun onCleared() {
        super.onCleared()
        userUpdateListenerRegistration?.remove()
    }

    fun logout() {
        authRepository.logout()
        _profileUiState.value = _profileUiState.value.copy(isLoggedOut = true)
    }
}