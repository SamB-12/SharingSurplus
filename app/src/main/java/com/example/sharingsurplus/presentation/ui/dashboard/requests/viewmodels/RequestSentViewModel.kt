package com.example.sharingsurplus.presentation.ui.dashboard.requests.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.requests.RequestSentScreenUiState
import com.example.sharingsurplus.data.states.status.ProduceStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RequestSentViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository
):ViewModel() {

    private val _requestSentUiState = MutableStateFlow(RequestSentScreenUiState())
    val requestSentUiState = _requestSentUiState.asStateFlow()

    init {
        getRequests()
    }

    private fun getRequests(){
        val requesterId = authRepository.currentUser?.uid

        viewModelScope.launch {
            firestoreRepository.getRequestsForRequester(requesterId!!).collectLatest {requestList ->
                _requestSentUiState.value = _requestSentUiState.value.copy(requestList = requestList)
            }
        }
    }

    fun onDeleteRequestDialogChange(value:Boolean){
        _requestSentUiState.value = _requestSentUiState.value.copy(isDeleteDialogVisible = value)
    }
    fun onEditRequestDialogChange(value:Boolean){
        _requestSentUiState.value = _requestSentUiState.value.copy(isEditDialogVisible = value)
    }

    fun onSelectedRequest(request: Request){
        _requestSentUiState.value = _requestSentUiState.value.copy(selectedRequest = request)
    }

    fun deleteRequest(){
        viewModelScope.launch {
            val result = firestoreRepository.deleteRequest(_requestSentUiState.value.selectedRequest!!.requestId)
            firestoreRepository.changeProduceStatus(_requestSentUiState.value.selectedRequest!!.produceId,ProduceStatus.Available)
            _requestSentUiState.value = _requestSentUiState.value.copy(deleteStatus = result)
        }
    }

    fun editRequest(){
        //TODO: Implement Editing the request
    }
}