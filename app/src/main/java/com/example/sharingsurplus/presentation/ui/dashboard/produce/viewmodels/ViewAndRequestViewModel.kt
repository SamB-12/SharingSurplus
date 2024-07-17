package com.example.sharingsurplus.presentation.ui.dashboard.produce.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.produce.ViewAndRequestProduceUiState
import com.example.sharingsurplus.data.states.status.ProduceStatus
import com.example.sharingsurplus.presentation.utils.GlobalVariables
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAndRequestViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val authRepository: AuthRepository
):ViewModel() {

    private val _viewAndRequestUiState = MutableStateFlow(ViewAndRequestProduceUiState())
    val viewAndRequestUiState = _viewAndRequestUiState.asStateFlow()

    init {
        _viewAndRequestUiState.value = ViewAndRequestProduceUiState(produceId = GlobalVariables.produceIdToView)
        viewModelScope.launch {
            getProduce()
        }
    }

    private suspend fun getProduce() {
        val result = firestoreRepository.getProduce(GlobalVariables.produceIdToView)

        if (result is AuthResult.Success){
            val produce = result.data
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(imageUrl = produce.produceImageUrl)
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(ownerId = produce.ownerId)
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(produceName = produce.produceName)
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(produceDescription = produce.produceDescription)
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(produceType = produce.produceType!!)
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(produceQuantity = produce.produceQuantity)
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(produceUnit = produce.produceUnit)
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(producePickupInstructions = produce.producePickupInstructions)
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(produceBestBeforeDate = produce.produceBestBeforeDate)
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(producerName = produce.producerName)
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(produceLocation = produce.produceLocation)
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(produceLatitude = produce.produceLatitude)
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(produceLongitude = produce.produceLongitude)
        }
    }

    fun requestProduce() {
        if (validateInput()) {
            if (validateRequestedQuantity()){
                viewModelScope.launch {
                    val requesterName = getRequesterName()
                    val request = Request(
                        produceId =_viewAndRequestUiState.value.produceId,
                        requesterId = authRepository.currentUser!!.uid,
                        ownerId = _viewAndRequestUiState.value.ownerId,
                        requestedDate = _viewAndRequestUiState.value.producePickUpDate,
                        requestedTime = _viewAndRequestUiState.value.producePickupTime,
                        requestedQuantity = _viewAndRequestUiState.value.requestedQuantity,
                        requestedImageUrl = _viewAndRequestUiState.value.imageUrl,
                        requestInstructions = _viewAndRequestUiState.value.producePickUpRequirements,
                        requesterName = requesterName,
                        requestedUnit = _viewAndRequestUiState.value.produceUnit,
                        ownerName = _viewAndRequestUiState.value.producerName,
                        produceName = _viewAndRequestUiState.value.produceName
                    )

                    firestoreRepository.createRequest(request)
                    firestoreRepository.changeProduceStatus(produceId = _viewAndRequestUiState.value.produceId, status = ProduceStatus.Requested)

                    _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(requestResult = AuthResult.Success(Unit))
                }
            } else {
                _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(requestResult = AuthResult.Error("Requested quantity exceeds produce quantity"))
            }
        } else {
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(requestResult = AuthResult.Error("Please fill in all fields"))
        }
    }

    private suspend fun getRequesterName(): String {
        val user = firestoreRepository.getUser(authRepository.currentUser!!.uid)
        if (user is AuthResult.Success){
            return user.data.name

        } else {
            return ""
        }
    }

    fun onPickUpDateChange(date: String) {
        _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(producePickUpDate = date)
    }

    fun onPickUpTimeChange(time: String) {
        _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(producePickupTime = time)
    }

    fun onPickUpRequirementsChange(instruction: String) {
        _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(producePickUpRequirements = instruction)
    }

    fun onDatePickerDialogChange(show: Boolean) {
        _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(isDatePickerVisible = show)
    }

    fun onTimePickerDialogChange(show: Boolean) {
        _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(isTimePickerVisible = show)
    }

    fun onRequestedQuantityChange(quantity: String) {
        try {
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(
                requestedQuantity = quantity.toInt()
            )
        } catch (e: Exception){
            _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(
                produceQuantity = 0
            )
        }
    }

    fun onConfirmDialogVisibleChange(show: Boolean) {
        _viewAndRequestUiState.value = _viewAndRequestUiState.value.copy(isConfirmDialogVisible = show)
    }

    private fun validateInput(): Boolean {
        if (_viewAndRequestUiState.value.producePickUpDate.isEmpty()
            || _viewAndRequestUiState.value.producePickupTime.isEmpty()
            || _viewAndRequestUiState.value.producePickUpRequirements.isEmpty()
            || _viewAndRequestUiState.value.requestedQuantity == 0
            || _viewAndRequestUiState.value.producePickUpRequirements.isEmpty()
            ){
            return false
        } else{
            return true
        }
    }

    private fun validateRequestedQuantity(): Boolean {
        if (_viewAndRequestUiState.value.requestedQuantity > _viewAndRequestUiState.value.produceQuantity){
            return false
        } else{
            return true
        }
    }

}