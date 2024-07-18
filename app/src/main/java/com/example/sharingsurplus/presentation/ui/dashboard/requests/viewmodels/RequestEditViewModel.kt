package com.example.sharingsurplus.presentation.ui.dashboard.requests.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.produce.ViewAndRequestProduceUiState
import com.example.sharingsurplus.data.states.dashboard.requests.RequestEditScreenUiState
import com.example.sharingsurplus.data.states.status.ProduceStatus
import com.example.sharingsurplus.presentation.utils.GlobalVariables
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestEditViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
): ViewModel() {

    private val _editRequestUiState = MutableStateFlow(RequestEditScreenUiState())
    val editRequestUiState = _editRequestUiState.asStateFlow()

    init {
        _editRequestUiState.value = _editRequestUiState.value.copy(produceId = GlobalVariables.produceIdToView, requestId = GlobalVariables.requestId)
        viewModelScope.launch {
            getRequest()
            getProduce()
        }
    }

    private suspend fun getRequest() {
        val result = firestoreRepository.getRequest(_editRequestUiState.value.requestId)

        if (result is AuthResult.Success) {
            val request = result.data

            _editRequestUiState.value = _editRequestUiState.value.copy(producePickUpDate = request.requestedDate)
            _editRequestUiState.value = _editRequestUiState.value.copy(producePickupTime = request.requestedTime)
            _editRequestUiState.value = _editRequestUiState.value.copy(producePickUpRequirements = request.requestInstructions)
            _editRequestUiState.value = _editRequestUiState.value.copy(requestedQuantity = request.requestedQuantity)
        }
    }

    private suspend fun getProduce() {
        val result = firestoreRepository.getProduce(_editRequestUiState.value.produceId)

        if (result is AuthResult.Success){
            val produce = result.data
            _editRequestUiState.value = _editRequestUiState.value.copy(imageUrl = produce.produceImageUrl)
            _editRequestUiState.value = _editRequestUiState.value.copy(ownerId = produce.ownerId)
            _editRequestUiState.value = _editRequestUiState.value.copy(produceName = produce.produceName)
            _editRequestUiState.value = _editRequestUiState.value.copy(produceDescription = produce.produceDescription)
            _editRequestUiState.value = _editRequestUiState.value.copy(produceType = produce.produceType!!)
            _editRequestUiState.value = _editRequestUiState.value.copy(produceQuantity = produce.produceQuantity)
            _editRequestUiState.value = _editRequestUiState.value.copy(produceUnit = produce.produceUnit)
            _editRequestUiState.value = _editRequestUiState.value.copy(producePickupInstructions = produce.producePickupInstructions)
            _editRequestUiState.value = _editRequestUiState.value.copy(produceBestBeforeDate = produce.produceBestBeforeDate)
            _editRequestUiState.value = _editRequestUiState.value.copy(producerName = produce.producerName)
            _editRequestUiState.value = _editRequestUiState.value.copy(produceLocation = produce.produceLocation)
            _editRequestUiState.value = _editRequestUiState.value.copy(produceLatitude = produce.produceLatitude)
            _editRequestUiState.value = _editRequestUiState.value.copy(produceLongitude = produce.produceLongitude)
        }
    }

    fun editRequest(){
        if (validateInput()){
            if (validateRequestedQuantity()){
                viewModelScope.launch {
                    firestoreRepository.updateRequest(
                        _editRequestUiState.value.requestId,
                        _editRequestUiState.value.producePickUpDate,
                        _editRequestUiState.value.producePickupTime,
                        _editRequestUiState.value.producePickUpRequirements,
                        _editRequestUiState.value.requestedQuantity
                    )

                    _editRequestUiState.value = _editRequestUiState.value.copy(requestEditResult = AuthResult.Success(Unit))
                }
            } else{
                _editRequestUiState.value = _editRequestUiState.value.copy(requestEditResult = AuthResult.Error("Requested quantity exceeds produce quantity"))
            }
        } else{
            _editRequestUiState.value = _editRequestUiState.value.copy(requestEditResult = AuthResult.Error("Please fill in all fields"))
        }
    }

    fun onPickUpDateChange(date: String) {
        _editRequestUiState.value = _editRequestUiState.value.copy(producePickUpDate = date)
    }

    fun onPickUpTimeChange(time: String) {
        _editRequestUiState.value = _editRequestUiState.value.copy(producePickupTime = time)
    }

    fun onPickUpRequirementsChange(instruction: String) {
        _editRequestUiState.value = _editRequestUiState.value.copy(producePickUpRequirements = instruction)
    }

    fun onDatePickerDialogChange(show: Boolean) {
        _editRequestUiState.value = _editRequestUiState.value.copy(isDatePickerVisible = show)
    }

    fun onTimePickerDialogChange(show: Boolean) {
        _editRequestUiState.value = _editRequestUiState.value.copy(isTimePickerVisible = show)
    }

    fun onRequestedQuantityChange(quantity: String) {
        try {
            _editRequestUiState.value = _editRequestUiState.value.copy(
                requestedQuantity = quantity.toInt()
            )
        } catch (e: Exception){
            _editRequestUiState.value = _editRequestUiState.value.copy(
                requestedQuantity = 0
            )
        }
    }

    fun onConfirmDialogVisibleChange(show: Boolean) {
        _editRequestUiState.value = _editRequestUiState.value.copy(isConfirmDialogVisible = show)
    }

    private fun validateInput(): Boolean {
        if (_editRequestUiState.value.producePickUpDate.isEmpty()
            || _editRequestUiState.value.producePickupTime.isEmpty()
            || _editRequestUiState.value.producePickUpRequirements.isEmpty()
            || _editRequestUiState.value.requestedQuantity == 0
            || _editRequestUiState.value.producePickUpRequirements.isEmpty()
        ){
            return false
        } else{
            return true
        }
    }

    private fun validateRequestedQuantity(): Boolean {
        if (_editRequestUiState.value.requestedQuantity > _editRequestUiState.value.produceQuantity){
            return false
        } else{
            return true
        }
    }

}