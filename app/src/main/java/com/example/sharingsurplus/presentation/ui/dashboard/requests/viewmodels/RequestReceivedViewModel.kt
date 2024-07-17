package com.example.sharingsurplus.presentation.ui.dashboard.requests.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.requests.RequestDisplayState
import com.example.sharingsurplus.data.states.dashboard.requests.RequestReceivedScreenUiState
import com.example.sharingsurplus.data.states.status.ProduceStatus
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

    fun onAcceptDialogVisibleChanged(isVisible: Boolean) {
        _requestReceivedUiState.value = _requestReceivedUiState.value.copy(
            isAcceptDialogVisible = isVisible
        )
    }

    fun onRejectDialogVisibleChanged(isVisible: Boolean) {
        _requestReceivedUiState.value = _requestReceivedUiState.value.copy(
            isRejectDialogVisible = isVisible
        )
    }

    fun onAcceptRequest() {
        if (validateReason()){
            viewModelScope.launch {
                val result = firestoreRepository.respondToRequest(
                    _requestReceivedUiState.value.selectedRequest.requestId,
                    RequestStatus.Accepted,
                    _requestReceivedUiState.value.reason
                )

                val produce = firestoreRepository.getProduce(_requestReceivedUiState.value.selectedRequest.produceId)

                if (produce is AuthResult.Success){
                    if (produce.data.produceQuantity>_requestReceivedUiState.value.selectedRequest.requestedQuantity){
                        firestoreRepository.changeProduceQuantity(
                            _requestReceivedUiState.value.selectedRequest.produceId,
                            produce.data.produceQuantity - _requestReceivedUiState.value.selectedRequest.requestedQuantity
                        )
                        firestoreRepository.changeProduceStatus(
                            _requestReceivedUiState.value.selectedRequest.produceId,
                            ProduceStatus.Available
                        )
                    } else{
                        firestoreRepository.changeProduceStatus(
                            _requestReceivedUiState.value.selectedRequest.produceId,
                            ProduceStatus.Agreed
                        )
                    }
                }

                _requestReceivedUiState.value = _requestReceivedUiState.value.copy(
                    acceptResult = result
                )
            }
        } else{
            _requestReceivedUiState.value = _requestReceivedUiState.value.copy(acceptResult = AuthResult.Error("Please fill out the reason"))
        }
    }

    fun onRejectRequest() {
        if (validateReason()){
            viewModelScope.launch {
                val result = firestoreRepository.respondToRequest(
                    _requestReceivedUiState.value.selectedRequest.requestId,
                    RequestStatus.Rejected,
                    _requestReceivedUiState.value.reason
                )

                firestoreRepository.changeProduceStatus(
                    _requestReceivedUiState.value.selectedRequest.produceId,
                    ProduceStatus.Available
                )

                _requestReceivedUiState.value = _requestReceivedUiState.value.copy(
                    rejectResult = result
                )
            }
        } else{
            _requestReceivedUiState.value = _requestReceivedUiState.value.copy(acceptResult = AuthResult.Error("Please fill out the reason"))
        }
    }

    fun onReasonChanged(reasonChanged: String) {
        _requestReceivedUiState.value = _requestReceivedUiState.value.copy(
            reason = reasonChanged
        )
    }

    fun onSelectedRequest(request: Request){
        _requestReceivedUiState.value = _requestReceivedUiState.value.copy(
            selectedRequest = request
        )
    }

    private fun validateReason(): Boolean {
        return _requestReceivedUiState.value.reason.isNotEmpty()
    }


}