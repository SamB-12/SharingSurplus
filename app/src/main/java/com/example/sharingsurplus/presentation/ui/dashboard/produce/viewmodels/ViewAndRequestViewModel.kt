package com.example.sharingsurplus.presentation.ui.dashboard.produce.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.produce.ViewAndRequestProduceUiState
import com.example.sharingsurplus.presentation.utils.GlobalVariables
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAndRequestViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
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

}