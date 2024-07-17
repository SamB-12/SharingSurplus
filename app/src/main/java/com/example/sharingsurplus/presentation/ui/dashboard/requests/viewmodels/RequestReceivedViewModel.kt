package com.example.sharingsurplus.presentation.ui.dashboard.requests.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.requests.RequestDisplayState
import com.example.sharingsurplus.data.states.dashboard.requests.RequestReceivedScreenUiState
import com.example.sharingsurplus.data.states.status.RequestStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestReceivedViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val authRepository: AuthRepository
):ViewModel() {

    private val _requestReceivedUiState = MutableStateFlow(RequestReceivedScreenUiState())
    val requestReceivedUiState = _requestReceivedUiState.asStateFlow()

    init {
        getRequests()
    }

    private fun getRequests() {
        val ownerId = authRepository.currentUser?.uid
        viewModelScope.launch {
            firestoreRepository.getRequestsForOwner(ownerId!!).collectLatest {requestList ->
                _requestReceivedUiState.value = _requestReceivedUiState.value.copy(
                    requestList = requestList
                )
            }
        }
    }

//    private fun setUiState(){
//        viewModelScope.launch {
//            _requestReceivedUiState.value.requestList.forEach { request ->
//                if (request.status == RequestStatus.Pending) {
//                    val produce = firestoreRepository.getProduce(request.produceId)
//                    val requester = firestoreRepository.getUser(request.requesterId)
//
//                    if (produce is AuthResult.Success && requester is AuthResult.Success){
//                        val displayUiToAdd = RequestDisplayState(
//                            produceId = request.produceId,
//                            requesterId = request.requestId,
//                            ownerId = request.ownerId,
//                            produceName = produce.data.producerName,
//                            requesterName = requester.data.name,
//                            requestedQuantity = request.requestedQuantity.toString() + " ${produce.data.produceUnit}",
//                            requestedTime = request.requestedTime,
//                            requestedDate = request.requestedDate,
//                            requestedRequirement = request.requestInstructions,
//                            imageUrl = produce.data.produceImageUrl
//                        )
//
//                        _requestReceivedUiState.value.displayList.add(displayUiToAdd)
//                    }
//                }
//            }
//        }
//    }

}