package com.example.sharingsurplus.presentation.ui.dashboard.requests.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.requests.RequestStatusScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestStatusViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val authRepository: AuthRepository
) : ViewModel(){

    private val _requestStatusState = MutableStateFlow(RequestStatusScreenUiState())
    val requestStatusState = _requestStatusState.asStateFlow()

    init {
        getRequests()
    }

    private fun getRequests(){
        val userId = authRepository.currentUser?.uid

        viewModelScope.launch {
            firestoreRepository.getRequestsForRequester(userId!!).collectLatest { requests ->
                _requestStatusState.value = _requestStatusState.value.copy(
                    requestList = requests
                )
            }
        }
    }
}